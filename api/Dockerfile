FROM node:20-alpine

# Set the working directory in the container
WORKDIR /app

# Copy only package.json and package-lock.json
COPY package.json package-lock.json ./

# Run npm install to install dependencies
RUN npm install

# Copy all other files into the container
COPY . .

# Expose port 3000
EXPOSE 3000

# Change the CMD to use host mode for Vite
CMD ["npm", "run", "dev", "--", "--host=0.0.0.0", "--port=3000"]
