# loose-coupling-demo

A hands-on Spring Boot project demonstrating the evolution from tightly coupled, hardcoded code to loosely coupled design using Interfaces, Spring IoC, Dependency Injection, and more.
Covers real-world patterns with clean examples across `@Component`, `@Autowired`, `@Qualifier`, `@Profile`, and `@Configuration`.

---

## Business Scenario

A **Payment Processing System** for an e-commerce application. The system processes payments through a payment gateway. The project demonstrates what happens when that gateway is hardcoded, and progressively shows better ways to design the system — ending with a fully loosely coupled, environment-aware implementation.

---

## What You Will Learn

- What tight coupling is and why it is a problem at scale
- How interfaces decouple the **what** from the **how**
- How Spring IoC eliminates manual object creation
- How `@Primary` and `@Qualifier` handle multiple implementations
- How `@Configuration` and `@Bean` centralize all wiring decisions
- How `@Profile` switches implementations based on environment

---

## Project Structure

```
src/main/java/com/example/loosecouplingdemo/
│
├── hardcoded/                        # Step 1 — Tight coupling, the problem
│   ├── RazorpayPaymentService.java
│   ├── OrderService.java
│   └── HardcodedRunner.java
│
├── interfaces/                       # Step 2 — Interface-based decoupling
│   ├── PaymentService.java           # Interface
│   ├── RazorpayPaymentService.java
│   ├── StripePaymentService.java
│   ├── OrderService.java
│   └── InterfaceRunner.java
│
├── springioc/                        # Step 3 — Spring IoC and @Autowired
│   ├── PaymentService.java
│   ├── RazorpayPaymentService.java   # @Component @Primary
│   ├── StripePaymentService.java     # @Component
│   ├── OrderService.java             # @Service @Autowired
│   └── SpringIocRunner.java
│
├── qualifier/                        # Step 4 — @Primary and @Qualifier
│   ├── PaymentService.java
│   ├── RazorpayPaymentService.java   # @Primary
│   ├── StripePaymentService.java
│   ├── PayPalPaymentService.java
│   ├── OrderService.java
│   └── QualifierRunner.java
│
├── configuration/                    # Step 5 — @Configuration and @Bean
│   ├── PaymentService.java
│   ├── RazorpayPaymentService.java   # Plain class — no @Component
│   ├── StripePaymentService.java     # Plain class — no @Component
│   ├── PayPalPaymentService.java     # Plain class — no @Component
│   ├── OrderService.java             # Plain class — no @Component
│   ├── PaymentConfig.java            # @Configuration — owns all wiring
│   └── ConfigurationRunner.java
│
├── profiles/                         # Step 6 — @Profile
│   ├── PaymentService.java
│   ├── MockPaymentService.java       # @Profile("dev")
│   ├── RazorpayPaymentService.java   # @Profile("prod")
│   ├── OrderService.java
│   └── ProfileRunner.java
│
└── LoosecouplingdemoApplication.java # Main entry point
```

---

## The 6 Steps

---

### Step 1 — Hardcoded / Tight Coupling

`OrderService` directly creates its own dependency using `new`.

```java
public class OrderService {
    // hardcoded — directly tied to Razorpay
    private RazorpayPaymentService paymentService = new RazorpayPaymentService();
}
```

**Problems this causes at scale:**

- Switching to Stripe requires editing every class that uses the gateway
- Cannot inject a mock in unit tests — tests hit real Razorpay logic
- Every class creates its own instance — no singleton, wasted memory
- Constructor changes in `RazorpayPaymentService` break every caller

---

### Step 2 — Interface (Loose Coupling)

`OrderService` depends on a `PaymentService` interface, not a concrete class. The implementation is passed in from outside.

```java
public class OrderService {
    private final PaymentService paymentService; // depends on abstraction

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService; // injected from outside
    }
}
```

**What this solves:** `OrderService` no longer knows which gateway it is using. Any implementation can be passed in.

**What this does not solve:** Someone still has to write `new RazorpayPaymentService()` somewhere. The problem is just moved, not eliminated.

---

### Step 3 — Spring IoC and `@Autowired`

No more `new`. Spring creates and injects everything automatically.

```java
@Service
public class OrderService {
    private final PaymentService paymentService;

    @Autowired
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService; // Spring injects this
    }
}
```

**What Spring does behind the scenes:**

1. Scans all packages for `@Component`, `@Service`, `@Repository`
2. Stores blueprints (`BeanDefinition`) in the `ApplicationContext`
3. Resolves dependency order
4. Calls constructors and creates singleton instances
5. Wires dependencies together
6. Serves beans via `@Autowired` wherever needed

---

### Step 4 — `@Primary` and `@Qualifier`

When multiple beans implement the same interface, Spring needs guidance on which one to inject.

```java
@Component
@Primary                          // default — Spring picks this when no qualifier given
public class RazorpayPaymentService implements PaymentService { }

@Component("stripePaymentService") // named bean — requested via @Qualifier
public class StripePaymentService implements PaymentService { }
```

```java
@Autowired
public OrderService(
        PaymentService defaultPaymentService,                            // @Primary — Razorpay
        @Qualifier("stripePaymentService") PaymentService stripeService  // explicit — Stripe
) { }
```

| Annotation | Use when |
|---|---|
| `@Primary` | One implementation is the default for the whole app |
| `@Qualifier` | You need a specific implementation at one injection point |

---

### Step 5 — `@Configuration` and `@Bean`

One central class owns all wiring decisions. Service classes have no Spring annotations — they are plain Java.

```java
@Configuration
public class PaymentConfig {

    @Bean
    @Primary
    public PaymentService configurationRazorpayPaymentService() {
        return new RazorpayPaymentService();
    }

    @Bean
    public OrderService configurationOrderService() {
        // switch to Stripe by changing this one line — nothing else changes
        return new OrderService(configurationRazorpayPaymentService());
    }
}
```

**When to use `@Bean` over `@Component`:**

| | `@Component` | `@Bean` |
|---|---|---|
| Where | On the class | On a method inside `@Configuration` |
| Use for | Your own classes | Third party libs, complex setup, central wiring |
| Wiring location | Scattered | One config file |

---

### Step 6 — `@Profile`

Spring automatically picks the right implementation based on the active environment.

```java
@Component
@Profile("dev")
public class MockPaymentService implements PaymentService {
    // no real API calls — safe for development and testing
}

@Component
@Profile("prod")
public class RazorpayPaymentService implements PaymentService {
    // real payment processing — only active in production
}
```

Switch environments in `application.properties` — zero code changes:

```properties
spring.profiles.active=dev   # uses MockPaymentService
spring.profiles.active=prod  # uses RazorpayPaymentService
```

---

## The Full Evolution

```
Step 1   OrderService creates its own dependency        you control everything
Step 2   Dependency passed in from outside              caller controls
Step 3   Spring creates and passes it in               Spring controls
Step 4   Spring picks the right implementation         Spring decides which
Step 5   One config file owns all decisions            one place controls
Step 6   Environment decides at startup                world controls
```

---

## Key Annotations — Cheat Sheet

| Annotation | Purpose |
|---|---|
| `@Component` | Marks a class as a Spring-managed bean |
| `@Service` | Specialization of `@Component` for service layer |
| `@Autowired` | Tells Spring to inject a dependency |
| `@Primary` | Default bean when multiple implementations exist |
| `@Qualifier("name")` | Pick a specific bean by name |
| `@Configuration` | Marks a class as a bean wiring blueprint |
| `@Bean` | Declares a bean inside a `@Configuration` class |
| `@Profile("env")` | Activates a bean only for a specific environment |
| `@Order(n)` | Controls execution order of `CommandLineRunner` beans |
| `@PostConstruct` | Runs setup code once after bean is created and injected |

---

## How to Run

### Prerequisites
- Java 17+
- Maven 3.8+

### Clone and Run

```bash
git clone https://github.com/your-username/loose-coupling-demo.git
cd loose-coupling-demo
./mvnw spring-boot:run
```

### Switch Profile

In `src/main/resources/application.properties`:

```properties
spring.profiles.active=dev    # MockPaymentService
spring.profiles.active=prod   # RazorpayPaymentService
```

### Run Tests

```bash
./mvnw test
```

---

## How Spring Manages Beans — Quick Reference

```
SpringApplication.run()
        ↓
ApplicationContext created — empty container
        ↓
Packages scanned — annotated classes found
        ↓
BeanDefinitions stored — blueprints only, no objects yet
        ↓
Dependency order resolved
        ↓
Constructors called — singleton objects created
        ↓
Dependencies injected — beans wired together
        ↓
@PostConstruct runs — setup code executes
        ↓
Auto-configuration — server, DB, security configured
        ↓
Embedded Tomcat starts on port 8080
        ↓
CommandLineRunners execute in @Order sequence
        ↓
Application ready
```

---

## Types of Dependency Injection

| Type | How | Recommended |
|---|---|---|
| Constructor injection | Via constructor parameter | Yes — field can be `final`, testable without Spring |
| Field injection | `@Autowired` directly on field | No — not testable, hides dependencies |
| Setter injection | `@Autowired` on setter method | Sometimes — for optional dependencies |

---

## Testing Approach

| Step | Test type | Key annotations |
|---|---|---|
| Step 1 — Hardcoded | Plain unit test | `@Test` |
| Step 2 — Interface | Mockito | `@Mock` `@InjectMocks` |
| Step 3 — Spring IoC | Mockito | `@Mock` `@InjectMocks` |
| Step 4 — Qualifier | Mockito | `mock()` manual wiring |
| Step 5 — Configuration | Mockito + Integration | `@SpringBootTest(classes = PaymentConfig.class)` |
| Step 6 — Profile | Integration | `@SpringBootTest` `@ActiveProfiles` |

---

## Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Framework 6.x
- JUnit 5
- Mockito
- Maven
