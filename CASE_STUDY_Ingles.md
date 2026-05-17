# Case Study: Inventory Management System

> **Professional portfolio document**
> This case study describes the context, technical decisions, and lessons learned behind the system available in this repository. Company name, specific products, and supplier names have been anonymized; the architecture and technical decisions are real.

---

## Executive Summary

I designed and built a web-based inventory management system for a **consumer goods distributor** (operating in the United States). The system replaced manual paper-based processes and informal spreadsheets with a centralized application featuring user roles, area-based stock control, advanced search, and PDF reporting.

**Tech stack:** Java 8, JSF 2.2, Hibernate 4.3, PrimeFaces 6.0, MySQL, GlassFish 4.1.1.

**My role:** Sole developer. Requirements analysis, database design, architecture, implementation, deployment.

---

## The Problem

When I started, the company's inventory control had several chronic issues:

- **No centralized digital system.** Stock was tracked on paper and personal spreadsheets that weren't shared between the warehouse, the office, and purchasing decision-makers.
- **No traceability.** Nobody could say with certainty how much of a given item was on hand, or when it had last been counted.
- **No accountability.** Anyone could change numbers without a record of who, when, or why.
- **No functional categorization.** Products from different areas (liquids, dry goods, cleaning supplies) got mixed in practice, making section-level counts painful.
- **Manual reporting.** Generating an inventory report required a full day of physical counting and Excel assembly.

I also identified **physical operational problems** that software alone couldn't solve, and pushed for fixes:

- **Insufficient storage capacity.** The main storage area was at capacity year-round. I recommended and got approval for an **external cold container** that expanded capacity significantly.
- **Lack of physical organization.** There was no shelving system, which made counts unreliable. I designed and got approval for a **shelving layout** that made counts consistent.

These physical changes were a prerequisite for the digital system to work well — software can have perfect data, but if the warehouse is disorganized, the data won't stay accurate.

---

## Process

### Analysis & Design (weeks 1-3)

Before writing any code, I interviewed the people who would actually use the system: whoever does the counts, whoever enters data, whoever reviews reports, whoever makes purchasing decisions. From this came the real requirements:

- **Two distinct user profiles.** An administrator (manages users, areas, configuration) and an operator (records inventory, runs queries, exports reports).
- **Multi-criteria search and filtering.** By product, date, area.
- **Automatic calculations.** Total value (quantity × weight × price) and aggregated weight. These used to be done by hand with frequent errors.
- **PDF export.** For the reports delivered to accounting.

### Architectural Decisions

**Why Java / JSF / Hibernate?**

At the time I was finishing my Computer Systems Engineering degree, and JSF/Hibernate were the technologies I had most experience with and best understood. For an internal business system, my priorities were:

- **Robust persistence with Hibernate ORM.** I needed the relationships (users → records, areas → products) to be consistent and changes to be tracked correctly. Hibernate gave me object-relational mapping without writing raw SQL for every operation.
- **Password encryption with BCrypt.** Even though it was an internal system, I wasn't going to store plaintext passwords. BCrypt with salting is industry standard.
- **Connection pooling with C3P0** (max 20, min 5, timeout 1800s). To avoid performance issues from repeated MySQL connections.
- **30-minute session timeout.** For security, especially for the admin role.
- **Role separation at the presentation layer.** Admin pages live under `/admin/` and operator pages under `/visitante/`. This makes URL-level access control straightforward.

### Data Model

Three main entities:

- **`Usuarios`** (Users) — authentication, contact info, role (`admin` / `visitor`)
- **`Area`** — functional categories (dry goods, cleaning, liquids, etc.)
- **`Inventarios`** (Inventory records) — the central record, with date, product, area, quantity, weight, price, and reference to the user who created it

The foreign keys from `Inventarios` point to `Area` and `Usuarios`, giving traceability: who recorded what and where. Each record has its own timestamp, allowing historical reconstruction.

### Implementation (3+ years of iteration)

The system wasn't built in one go. It started as a simple MVP (basic CRUD, authentication, areas) and evolved with real-world use. Key iterations:

- **v1:** Basic CRUD, single-role login.
- **v2:** Role separation, admin/operator page split, password change separated from user CRUD.
- **v3:** Advanced search with combinable filters.
- **v4:** PDF export via PrimeFaces.
- **v5+:** UX refinements based on real feedback ("we want to see the total while filtering," "we want to search by month," etc.).

---

## Results & Impact

- **Report generation time:** went from a full day of manual counting and Excel assembly to minutes (query, filter, export PDF).
- **Centralized data:** a single source of truth. The warehouse sees the same numbers as the office.
- **Access control:** the operator role can view and record inventory without touching critical configuration.
- **Traceability:** every record has a date and user, which surfaced errors and patterns that had previously gone unnoticed.
- **Visual audit:** filtered queries made it obvious when physical counts diverged from digital records.

---

## Lessons Learned

**Software doesn't solve software problems alone.** A big part of this project's success was identifying the physical problems (storage capacity, lack of shelving) and pushing for them to be solved alongside the software. A perfect digital system on top of a disorganized warehouse produces unreliable data. This taught me to look at the whole problem, not just the part that fits on a screen.

**Iteration with real users is priceless.** Early versions had features I thought would be useful and weren't, while things missing only came up once people used the system daily. The gap between "what users say they need" and "what they actually need" is found through use, not interviews.

**Simple, known decisions beat new and flashy ones.** I could have picked a more modern stack (React, Spring Boot, microservices) but it would have added complexity without clear benefit for an internal system of this size. JSF + Hibernate + MySQL + GlassFish was a mature, stable stack I could maintain alone. The right decision is sometimes the least exciting one.

**Being the sole developer requires more than code.** I did analysis, design, implementation, deployment, support, and user training. That taught me discipline around documentation, maintainability, and not building things I couldn't later explain.

---

## Current State & Next Steps

This system (Java/JSF) was the first version. I later designed a **second generation** with significant improvements:

- Finer-grained roles (admin, suppliers with views limited to their own products, areas/rooms with views limited to their section)
- Stock status indicator system (critical / low / OK) with configurable thresholds and different rules per area type (storage areas by quantity + weight, processing rooms by weight only)
- Date-based data versioning: editing with a new date creates a new historical version rather than overwriting
- Full audit trail with mandatory reason for every change
- Time-based filters by week, month, and specific day

That second iteration is documented in a separate repository.

---

## Methodology & Tools

I want to be transparent about how I worked on this project, because I think it matters for understanding what my real contribution was:

- **Problem analysis and solution design:** mine. The user interviews, the identification of physical problems (capacity, shelving), the data model, the user roles, the workflows, and the architectural and stack decisions all came from the real context I lived inside the company.
- **Code implementation:** I worked with AI assistants as a development tool, which is standard practice in the current software industry. The decisions of what to build, how to structure it, which libraries to use, and how to integrate it with the business problem were mine. I understand the code in this repository and can walk through any part of it.
- **Iteration and maintenance:** mine. The 3+ years of real-world use, with the errors, adjustments, and feedback-driven improvements, is the hardest part to fake — and where I learned the most.

If you're interested in this project and want to see a live demo or review the code in a technical interview, I'm available.

---

## Tech Stack (detailed)

| Layer | Technology | Version |
|---|---|---|
| Language | Java | 8+ |
| Web framework | JSF | 2.2 |
| UI components | PrimeFaces | 6.0 |
| ORM | Hibernate | 4.3.x |
| Encryption | BCrypt | — |
| Connection pool | C3P0 | — |
| Database | MySQL | 5.7+ |
| Application server | GlassFish | 4.1.1 |
| IDE | NetBeans | 8.2 |

---



