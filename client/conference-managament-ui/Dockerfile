FROM node:12

WORKDIR /usr/src/app

COPY package*.json ./

RUN npm install
RUN npm install --save react-router-dom
RUN npm install --save reactstrap
RUN npm install --save bootstrap

COPY . .


CMD [ "npm", "start" ]