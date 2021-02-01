# MOVIES

An android application to get popular movies from ```themoviedb``` API
using clean architecture for separation of code in different layers with assigned responsibilities in order to make it easier for further modification.


## Installation

Just one step to clone the repo as below

```bash
git clone https://github.com/gibranhit/Movies-Challenge.git
```
It's recommend to have the latest version of android studio **3.5.1**


## Project structure

As we mentioned before we're taking into account SOLID principles and clean architecture and of course clean code. The project structure contains the following layers:

1. **UI (Presentation)**
   - Includes a reference of the domain and data layer. It's an android specific layer which executes the UI logic. This layer also contains the implementation of the architectural pattern which is **MVVM** as required in the code challenge.

2. **Domain**
     - It is just a pure kotlin package with no android specific dependency. It contains the execution of the business rules (use case) .
     - Contains the definition of the business entities. Specifically in this app contains the entity  **Movie.**


3. **Data**
   - Provides the required data for the application to the domain layer by implementing interfaces (**repositories**).

   - This layer is basically divided in 2 packages. The first one contains the implementation for the local data source (**room**) and the second contains the implementation for the remote data source (**retrofit**)


## Build with

* [Kotlin](#) - Main programming language
* [MVVM](#) - Architectural pattern used
* [Koin](#) - Framework for Dependency Injection
* [Room](#) - To store local data
* [Retrofit](#) - To make network requests
* [Coroutines](#) - To handle async calls
* [Jetpack](#) - Used in conjunction with MVVM

## Author

Hector Gibran Reyes Alvarez
* [Linkedin](https://www.linkedin.com/in/gibran-reyes-429992171/)


## License
[MIT](https://github.com/gibranhit/Movies-Challenge/blob/master/LICENSE)


## Screenshot
<img src="https://github.com/gibranhit/Movies-Challenge/blob/master/peliculas_populares.jpeg" width="300" height="600" title="hover text">
<img src="https://github.com/gibranhit/Movies-Challenge/blob/master/peliculas_favoritas.jpeg" width="300" height="600" title="hover text">
<img src="https://github.com/gibranhit/Movies-Challenge/blob/master/detalles_pelicula.jpeg" width="300" height="600" title="hover text">
