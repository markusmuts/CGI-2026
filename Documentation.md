# Documentation

### Prerequisites

### Workflow
#### Step 1: Initializing the Spring Boot and React Native Apps
I recently started using **WSL** for a more performant development experience and had to fix a few issues that came up. I used the ``Spring Initializr Java Support`` plugin to generate the backend and had to run a few commands to generate the frontend. To verify the connectivity between the two, I implemented a simple ``HelloController.java`` class to ensure the apps could communicate effectively.

#### Step 2: Designing the Backend
After learning the fundamentals of Spring Boot, I asked AI to generate me a good backend structure without providing me the code. Once the foundation was set, the implementation was quite straightforward. During this phase, I also verified my progress by displaying randomly generated tables in a list on the frontend, confirming that the data was flowing correctly from the database to the UI.

#### Step 3: Reservation finding and filtering
In this phase, I built the reservation availability feature by connecting frontend filters to a backend endpoint that returns available start times based on date, guest count, and optional location of the tables. The logic checks existing reservations for overlaps, applies different weekday and weekend time limits, and uses shorter daytime and longer evening reservation durations. I took inspiration from existing reservation systems to design my own.

#### Step 4: Displaying available tables on a floor plan
I tried very different options but decided to create a grid from tables to display the floor plan. I also decided that I should hard code the features of the tables so the restaurant won't have, for example, seven 8-seat tables. It took some time to also make the grid visually appealing and make the app usable on smaller screens.

### Hard parts
- **Learning React and Spring Boot from scratch** - I had to read more about them and watch quite a few tutorials.
- **Initial backend structure** - Since I was not familiar with Spring Boot, I had to do some research before coming up with the structure. I had to understand what each component does and how they work together.
- **Full-Stack Communication** - While I have experience with web applications, this was my first time building the bridge between a frontend and a backend entirely from scratch.
- **Thinking of everything that the user could do wrong**
- **Creating good UI/UX**


### References to foreign code
#### AI generated code:
- ``backend/src/main/java/com/cgi/backend/config/DataSeeder.java`` - everything
- ``backend/src/main/java/com/cgi/backend/service/TimeAvailabilityService.java`` - everything
- ``frontend/src/App.css`` - everything
- ``frontend/src/App.jsx`` - references are in the code


### Time spent: ≈16 hrs