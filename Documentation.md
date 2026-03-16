# Documentation

### Prerequisites

### Workflow
#### Step 1: Initializing the Spring Boot and React Native Apps
I recently started using **WSL** for a more performant development experience and had to fix a few issues that came up. I used the ``Spring Initializr Java Support`` plugin to generate the backend and a had to run a few commands to generate the frontend. To verify the connectivity between the two, I implemented a simple ``HelloController.java`` class to ensure the apps could communicate effectively.

#### Step 2: Designing the Backend
After learning the fundamentals of Spring Boot, I asked AI to generate me a good backend structure without providing me the code. Once the foundation was set, the implementation was quite straightforward. During this phase, I also verified my progress by displaying randomly generated tables in a list on the frontend, confirming that the data was flowing correctly from the database to the UI.

### Hard parts
- **Learning React and Spring Boot from scratch** - I had to read more about them and watch quite a few tutorials.
- **Initial backend structure** - Since I was not familiar with Spring Boot, I had to do some research before coming up with the structure. I had to understand what each component does and how they work together.
- **Full-Stack Communication** - While I have experience with web applications, this was my first time building the bridge between a frontend and a backend entirely from scratch.


### References to foreign code
#### AI generated code:
- ``backend/src/main/java/com/cgi/backend/config/DataSeeder.java`` - everything
- ``frontend/src/App.css`` - lines 187 to 208

### Time spent: ≈9 hrs