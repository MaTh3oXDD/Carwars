FROM node:20-alpine

# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install
COPY . .

EXPOSE 3000

# Change the CMD to use host mode for Vite
CMD ["npm", "run", "dev", "--", "--host"]