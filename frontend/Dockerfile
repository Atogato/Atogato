
# Pull Node apline
FROM node:16-alpine

WORKDIR /app

RUN apk add --update --no-cache \
    build-base \
    cairo-dev \
    pango-dev \
    jpeg-dev \
    giflib-dev \
    librsvg-dev

# COPY [host machine] -> [conatiner] (path relative to  dockerfil)
COPY package.json yarn.lock ./

# COPY all files in directory to Docker work directory
COPY ./ .

# will install all dependency in package.json
RUN yarn install

COPY next.config.js ./next.config.js

CMD ["yarn", "dev"]

EXPOSE 3000
