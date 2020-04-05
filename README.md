# CONFERENCE MANAGER
Süreleri belirli olanlar konferans oturumlarının sistem tarafından zaman planlanması yapılmaktadır. Oturumlar kullanıcı tarafından web arayüzü aracılığıyla eklenmektedir ve silinmektedir. Uygulamada üç farklı modül bulunmaktadır. Bunlar *DB*, *Server* ve *Client*'tır. 

Demo: https://youtu.be/CMvQ-y0LEEc

## Kurulum Aşamaları
 
### 1. DB

- Command Prompt(cmd) açılır. Ve aşağıdaki komutlar sırasıyla çalıştırılır.
> cd Conference-Manager/db

> docker-compose up -d

Böylece SQL Server 2017 yüklenmiş olur. Fakat database oluşturulması gerekmektedir. Bunun için aynı klasörün içerisindeki **setup.sql** dosyasının çalıştırılması gerekmektedir.


### 2. Server
Intellij Idea üzerinde **Spring-Boot** ile geliştirilmiştir. **Hibernate** kullanılarak tablolar otomatik olarak oluşturulmaktadır. Client ile iletişim için **Rest-API** kullanılmıştır. **JUnit4** ile API test metdoları yazılmıştır.

Veritabanına bağlantı bilgileri *"Conference-Manager/server/managing-conference/src/main/resources/application.properties"* dosyasından değiştirilebilmektedir.

- Docker kullanarak çalıştırmak için aşağıdaki komutları çalıştırılmalıdır.

> cd Conference-Manager/server/managing-conference

> mvn clean install

> docker build -t serverimage .

> docker run -d -p 8080:8080 --name servercontainer serverimage


### 3. Client
React.js ile geliştirilmiştir. *Client-Module/conference-managament-ui/src* klasörünün içerisindeki JavaScript ve CSS dosyları üzerinden geliştirmeler yapılmıştır.

- Docker üzerine kurulum için aşağıdaki komutlar girilmelidir.

> cd Conference-Manager/client/conference-managament-ui

> docker build -t clientimage .

> docker run -d -p 3000:3000 --name clientcontainer clientimage

- Windows üzerine kurulum için aşağıdaki adımlar izlenmelidir;

> Öncelikle https://nodejs.org/en/ adresinden 12.61.1 LTS kurulum dosyası indirilip çalıştırılmalıdır.

> Command Prompt(cmd) açılır.

> npx install –g create-react-app

> "Client-Module" isminde bir dizin oluşturulur.

> cd Client-Module

> npx create-react-app conference-managament-ui

> cd conference-managament-ui

> npm install --save react-router-dom

> npm install --save reactstrap

> npm install --save bootstrap

> Repository'den indirilen *"Conference-Manager/client/conference-managament-ui"* klasörünün içerisindeki dosyalar *"Client-Module/conference-managament-ui"* klasörü içerisine yüklenir(yer değiştirilir).

> npm start


Web arayüzü http://localhost:3000 üzerinde çalışmaya başlayacaktır.


