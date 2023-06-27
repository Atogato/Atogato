
# Pull Node apline
FROM node:16-alpine

WORKDIR /app

# COPY [host machine] -> [conatiner] (path relative to  dockerfil)
COPY package.json yarn.lock ./

# COPY all files in directory to Docker work directory
COPY ./ .

# will install all dependency in package.json
RUN yarn install

COPY next.config.js ./next.config.js

CMD ["yarn", "dev"]

EXPOSE 3000
