FROM maven
COPY apiKey.txt .
COPY . /telegram-news-bot
RUN cd telegram-news-bot && \
    mvn package
CMD cd telegram-news-bot && \
    mvn exec:java
