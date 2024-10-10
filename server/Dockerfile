FROM openjdk:22

RUN mkdir -p /opt/user/server
COPY lib/fastcgi-lib.jar /opt/user/server/lib/fastcgi-lib.jar
COPY src/ /opt/user/server/src
WORKDIR /opt/user/server/

RUN mkdir -p out
RUN javac -d out -cp lib/fastcgi-lib.jar src/*.java

# Распаковываем классы из fastcgi-lib.jar в текущую директорию
RUN jar -xf lib/fastcgi-lib.jar

# Создаем директорию для пакета com
RUN mkdir -p lib/com
# Перемещаем распакованные классы из com/fastcgi в lib/com/
RUN mv com/fastcgi lib/com/

RUN echo "Main-Class: Main" > MANIFEST.mf

# Создаем общий JAR-файл server.jar
RUN jar -cvfm server.jar MANIFEST.mf -C out . -C lib/ .

# Удаляем временные директории и файлы, чтобы уменьшить размер образа
RUN rm -rf lib/ src/ out/ com/

EXPOSE 1537

CMD ["java", "-DFCGI_PORT=1537", "-jar", "server.jar"]
