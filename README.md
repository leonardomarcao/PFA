[![MIT License](https://img.shields.io/badge/license-ASF2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.txt)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d113af0da84b4ab9b17b8ffc29c58ecf?branch=admin-javafx)](https://www.codacy.com/app/tiagohs/stuffs-admin?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=tiagohs/stuffs-admin&amp;utm_campaign=Badge_Grade)
## Personal Financial Assistant
<p align="center">
  <img src="https://i.imgur.com/nvRjXPj.png" alt="">
  <span size="6">PFA - Login</span>
</p>

<p align="center">
  <img src="https://i.imgur.com/IaBtkuF.png" alt="">
  <span size="6">PFA - ADMIN PANEL (contains: Dashboard, Despesa and Receita)</span>
</p>

### About

The Personal Financial Assistant's main objective is to optimize the flow of expenses and revenues to help you control your expenses. The system is completely independent, not limited to static data, such as registering different types of expenses and revenues, allowing the generation of dynamic reports.

### How does it work?

The PFA was architected and developed to be as organized and streamlined as possible. We follow a whole process of development, workflow, software architecture and recent tools. The result obtained through this is the wide maintainability of the same.

### Installation 

#### Requirements
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

### License 

MIT License

Copyright (c) 2018 Leonardo Marcão

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
