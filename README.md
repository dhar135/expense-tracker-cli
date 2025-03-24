# Expense Tracker

A command-line expense tracking application built with Spring Shell and Spring Data JPA. This project is inspired by [roadmap.sh's Expense Tracker Project](https://roadmap.sh/projects/expense-tracker).

## Features

- Track personal expenses with descriptions, amounts, dates, and categories
- Add, update, and delete expenses through an interactive shell interface
- View all expenses in a formatted table
- Get expense details by ID
- Filter expenses by category
- View expense summaries (total and by month)
- Set and manage monthly budgets by category
- Track spending against budgets
- File-based H2 database for persistence

## Tech Stack

- Java 23
- Spring Boot 3.4.3
- Spring Shell 3.4.0
- Spring Data JPA
- H2 Database
- Gradle
- JUnit 5 and Mockito for testing

## Getting Started

### Prerequisites

- Java 23 or higher
- Gradle (or use the included Gradle wrapper)

### Running the Application

1. Clone the repository

```bash
git clone https://github.com/dhar135/expense-tracker.git
cd expense-tracker
```

2. Build the project

```bash
./gradlew build
```

3. Run the application

```bash
java -jar build/libs/expense_tracker-0.2.0.jar 
```

## Shell Commands

| Command | Description | Parameters |
|---------|-------------|------------|
| `add` | Add a new expense | `--description` (text), `--amount` (decimal), `--category` (optional) |
| `update` | Update an existing expense | `--id` (long), `--description` (optional), `--amount` (optional), `--category` (optional) |
| `delete` | Delete an expense | `--id` (long) |
| `list` | List all expenses | none |
| `get` | Get an expense by ID | `--id` (long) |
| `summary` | Get expense totals | `--month` (optional, integer 1-12) |
| `category` | Get expenses by category | `--category` (enum value) |
| `create-budget` | Create a budget | `--amount` (decimal), `--category` (enum value), `--period` (Month: 1-12 or JANUARY-DECEMBER) |
| `update-budget` | Update a budget | `--id` (long), `--amount` (optional), `--category` (optional), `--period` (optional) |
| `delete-budget` | Delete a budget | `--id` (long) |
| `get-budget` | Get a budget by ID | `--id` (long) |
| `get-all-budgets` | List all budgets | none |
| `get-budgets-by-category` | Get budgets by category | `--category` (enum value) |

## Available Categories

- GROCERIES
- UTILITIES
- RENT
- ENTERTAINMENT
- TRANSPORTATION
- HEALTH
- INSURANCE
- OTHER

## Examples

```bash
# Add an expense
add --description "Grocery shopping" --amount 45.50 --category GROCERIES

# Update an expense (only changing the amount)
update --id 1 --amount 50.25

# Get a summary for March
summary --month 3

# List all expenses
list

# View expenses in the entertainment category
category --category ENTERTAINMENT

# Create a budget for groceries in January
create-budget --amount 300.00 --category GROCERIES --period JANUARY

# Get all current budgets
get-all-budgets

# Update a budget (changing only the amount)
update-budget --id 1 --amount 350.00
```

## Project Structure

- `src/main/java/io/github/dhar135/expense_tracker/`
  - `model/` - Entity and enum classes
  - `repository/` - Data access layer
  - `service/` - Business logic
  - `shell/` - Command line interface
  - `budget/` - Budget related components

## Testing

The application includes unit tests for all major components. Run tests with:

```bash
./gradlew test
```

## Future Features

- Allow users to export expenses to a CSV file.

## License

This project is available under the MIT License.
