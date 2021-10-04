# result

[![Build Status](https://app.travis-ci.com/ashatch/result.svg?branch=main)](https://app.travis-ci.com/ashatch/result)

`Result` monad with two subtypes `Success` and `Failure`.

## Example

See [the tests](src/test/java/net/andrewhatch/result) for details on how to use `Result`.

## Usage

### Maven

```xml
<project>
    <repositories>
      <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
      </repository>
    </repositories>

    <dependency>
      <groupId>com.github.ashatch</groupId>
      <artifactId>result</artifactId>
      <version>main-SNAPSHOT</version>
    </dependency>
</project>
```

### Gradle

```groovy
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  implementation 'com.github.ashatch:result-monad:main-SNAPSHOT'
}
```
