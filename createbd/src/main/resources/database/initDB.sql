CREATE TABLE IF NOT EXISTS CryptocurrencyPriceTable
(
    id    BIGSERIAL PRIMARY KEY ,
    title  VARCHAR(200) NOT NULL ,
    USDprice float8 NOT NULL ,
    EURprice float8 NOT NULL ,
    RUBprice float8 NOT NULL
);