## Personal Financial Assistant
<p align="center"><img src="https://i.imgur.com/nvRjXPj.png" alt=""></a></p>

### About

The Personal Financial Assistant's main objective is to optimize the flow of expenses and revenues to help you control your expenses. The system is completely independent, not limited to static data, such as registering different types of expenses and revenues, allowing the generation of dynamic reports.

### How does it work?

The PFA was architected and developed to be as organized and streamlined as possible. We follow a whole process of development, workflow, software architecture and recent tools. The result obtained through this is the wide maintainability of the same.

###

### How to configure?

####Requirements
1. Hibernate 5.3.6
2. JavaFx 
3. JDK 10.0.2
4. JPA 2.2
4. fontawesomefx 9.1.2
5. jfoenix 9.0.6 (Used for customization Material Design)
7. postgresql 42.2.5
8. Eclipse

#### Downloading and Extracting PFA

1. Download [PFA]
3. Extract the PFA-master.zip

#### Setting Up PFA Project
1. Open up Eclipse.
2. Open project.
3. Verify and set up dependences libraries 
4. Add source folder called "resources" (the configuration will used by Hibernate)
5. Go to in Run configuration (using Eclipse IDE) and add the follow JVM arguments: --add-modules java.xml.bind (this is caused by JDK 10.0.2, because Hibernate tries to use for the "hibernate.cfg. xml)
