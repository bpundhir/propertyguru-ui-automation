# PropertyGuru UI Automation

A Java Playwright + TestNG + Maven foundation for automating the PropertyGuru
search and filter journey, structured so the core framework can later support
similar CARS24 workflows.

## Stack

- Java 17 language level
- Playwright for Java `1.60.0`
- TestNG `7.12.0`
- Apache Maven `3.9.16` via Maven Wrapper `3.3.4`

## Project Structure

```text
src/main/java/com/cars24/automation/
  framework/
    config/       External configuration loading and system-property overrides
    driver/       DriverManager for Playwright lifecycle
    reporting/    Extent reporting, screenshot and trace artifacts
src/test/java/com/cars24/automation/
  framework/
    assertions/   Custom assertion wrapper
    base/         BaseClass for TestNG browser and report hooks
  tests/          TestNG tests
src/test/resources/
  config/         Environment-specific values
  suites/         TestNG suite definitions
```

## Run Tests

The suite includes a configuration check and a browser smoke test that opens
the configured PropertyGuru sale-listings route.

```bash
./mvnw clean test
```

The default `qa` profile launches Chromium visibly with a short slow-motion
delay and close delay so local UI execution can be observed. Extent HTML
reports are written to `target/reports/extent-report.html`.

Before executing UI tests added in the next step, install Playwright browser
binaries once:

```bash
./mvnw exec:java -Dexec.mainClass=com.microsoft.playwright.CLI \
  -Dexec.classpathScope=test -Dexec.args="install chromium"
```

For non-visual execution, such as CI:

```bash
./mvnw test -Dheadless=true -Dslow.motion.ms=0
```

## Configuration

Runtime values are kept in
`src/test/resources/config/qa.properties`. Each property may be overridden
without editing source code:

```bash
./mvnw test -Dbrowser=firefox -Dheadless=false -Dslow.motion.ms=250
./mvnw test -Dbase.url=https://example.test -Dsale.listings.path=/cars \
  -Dexpected.sale.listings.path=/cars
```

Add another profile such as `staging.properties` in the same directory and
select it with:

```bash
./mvnw test -Denvironment=staging
```

## Adding Search And Filter Tests

1. Extend `BaseClass` and navigate to URLs loaded through `FrameworkConfig`.
2. Use `AssertionUtil` inside tests so expected failures carry test-specific
   messages and can be logged in the test's assertion `catch` block.
3. Store test inputs in a TestNG data provider or resource file, rather than
   embedding search terms and filter amounts inside test methods.
4. Add the test class to `src/test/resources/suites/testng.xml`.

`BaseClass` creates an isolated browser context per test method. Failed UI
tests write screenshots and Playwright trace archives under `target/artifacts`,
and screenshots are attached to the Extent HTML report.
