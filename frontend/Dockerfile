
# Pull Node apline
FROM node:17-alpine3.15

# COPY [host machine] -> [conatiner] (path relative to  dockerfil)
COPY package.json yarn.lock .

# will install all dependency in package.json
RUN npm install

COPY . .

RUN npm run build