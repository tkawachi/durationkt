# durationkt

A library to handle duration.
It's greatly inspired by scala.concurrent.duration.FiniteDuration.

## Usage

```kotlin
import com.github.tkawachi.durationkt.*

1.second // -> Duration(1, TimeUnit.SECONDS)

1.second + 2.seconds // -> Duration(3, TimeUnit.SECONDS)

1.second + 1.minute // -> Duration(61, TimeUnit.SECONDS)

1.second * 10 // -> Duration(10, TimeUnit.SECONDS)

1.minute - 1.second // -> Duration(59, TimeUnit.SECONDS)

1.second.toMillis() // -> 1000L
```
