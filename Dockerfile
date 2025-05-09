# Используем официальный JDK-образ
FROM openjdk:17-jdk-slim

# Рабочая директория в контейнере
WORKDIR /app

# Копируем jar-файл приложения (предположим, он называется app.jar)
COPY target/app.jar app.jar

# Команда запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
