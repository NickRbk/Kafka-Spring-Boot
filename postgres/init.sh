#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE resourcedb;
    GRANT ALL PRIVILEGES ON DATABASE resourcedb TO nick;

    CREATE DATABASE kafka;
    GRANT ALL PRIVILEGES ON DATABASE kafka TO nick;

    \c resourcedb

    CREATE TABLE resources (
        id serial PRIMARY KEY,
        url VARCHAR(300)
    );

    INSERT INTO resources (url) VALUES ('http://www.0629.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.061.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.62.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.057.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.048.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.056.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0564.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.44.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0642.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0512.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.032.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0352.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.06242.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0542.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.05447.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.06274.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.05366.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.06239.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.6264.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0432.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0532.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0623.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0552.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.5692.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0522.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0462.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.6262.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0412.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0372.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0332.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0362.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0342.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0472.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.06277.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.06252.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.06153.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.04868.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.06236.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0312.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.06432.in.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.04565.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.3131.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.06257.in.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0382.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.0566.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.06434.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.5632.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.04578.in.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.06452.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.4594.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.04141.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.04563.com.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.3652.ru/rss');
    INSERT INTO resources (url) VALUES ('http://ura-inform.com/rss/ru/rss.xml');
    INSERT INTO resources (url) VALUES ('http://cripo.com.ua/export/rss.xml');
    INSERT INTO resources (url) VALUES ('http://zaxid.net/rss/1.xml');
    INSERT INTO resources (url) VALUES ('http://zik.ua/rss/export.rss');
    INSERT INTO resources (url) VALUES ('http://zn.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.president.gov.ua/rss/news/all.rss');
    INSERT INTO resources (url) VALUES ('https://www.gp.gov.ua/ua/news.html?rss=gp_55355');
    INSERT INTO resources (url) VALUES ('http://www.ombudsman.gov.ua/ua/rss.rss');
    INSERT INTO resources (url) VALUES ('http://112.ua/rss/index.rss');
    INSERT INTO resources (url) VALUES ('http://ru.espreso.tv/rss');
    INSERT INTO resources (url) VALUES ('http://24tv.ua/rss/all.xml');
    INSERT INTO resources (url) VALUES ('http://k.img.com.ua/rss/ru/ukraine.xml');
    INSERT INTO resources (url) VALUES ('http://gordonua.com/xml/rss.html');
    INSERT INTO resources (url) VALUES ('http://obozrevatel.com/rss.xml');
    INSERT INTO resources (url) VALUES ('http://rss.unian.net/site/news_rus.rss');
    INSERT INTO resources (url) VALUES ('http://fakty.ua/rss_feed/all');
    INSERT INTO resources (url) VALUES ('http://gazeta.ua/ru/rss');
    INSERT INTO resources (url) VALUES ('http://lb.ua/rss/rss.xml');
    INSERT INTO resources (url) VALUES ('http://www.unn.com.ua/rss/news_uk.xml');
    INSERT INTO resources (url) VALUES ('http://onpress.info/feed');
    INSERT INTO resources (url) VALUES ('http://24portal.net/feed');
    INSERT INTO resources (url) VALUES ('http://telegraf.com.ua/yandex-feed/');
    INSERT INTO resources (url) VALUES ('http://interfax.com.ua/news/last.rss');
    INSERT INTO resources (url) VALUES ('http://focus.ua/modules/rss.php');
    INSERT INTO resources (url) VALUES ('http://tsn.ua/rss');
    INSERT INTO resources (url) VALUES ('http://www.rbc.ua/static/rss/all.ukr.rss.xml');
    INSERT INTO resources (url) VALUES ('https://www.pravda.com.ua/rss/');
    INSERT INTO resources (url) VALUES ('http://kp.ua/rss/feed.xml');
    INSERT INTO resources (url) VALUES ('http://ipress.ua/rss/export.rss');
    INSERT INTO resources (url) VALUES ('http://glavnoe.ua/rss/newsall.xml');
    INSERT INTO resources (url) VALUES ('http://www.sq.com.ua/rss/rss_all.xml');
    INSERT INTO resources (url) VALUES ('http://www.dni.ru/rss.xml');
    INSERT INTO resources (url) VALUES ('http://reporter-ua.com/xml_export/yandex');
    INSERT INTO resources (url) VALUES ('http://hvylya.net/feed');
    INSERT INTO resources (url) VALUES ('http://replyua.net/rss.xml');
    INSERT INTO resources (url) VALUES ('http://ilich.in.ua/rss');
    INSERT INTO resources (url) VALUES ('http://kaniv.net/kaniv_feed0.xml');
EOSQL