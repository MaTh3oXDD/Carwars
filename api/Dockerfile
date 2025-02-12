# Użycie oficjalnego obrazu Node.js w lekkiej wersji Alpine
FROM node:20-alpine AS base

# Ustawienie katalogu roboczego w kontenerze
WORKDIR /app

# Skopiowanie zależności (package.json i package-lock.json)
COPY package.json package-lock.json ./

# Instalacja zależności
RUN npm install

# Skopiowanie pozostałych plików do kontenera
COPY . .

# Eksponowanie portu aplikacji React
EXPOSE 3000

# Zmienna środowiskowa dla poprawnego działania Hot Reloading
ENV CHOKIDAR_USEPOLLING=true

# Uruchomienie serwera deweloperskiego Vite
CMD ["npm", "run", "dev"]
