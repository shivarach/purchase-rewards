# purchase-rewards

Containerized spring boot application to calculate rewards for customer purchases. It uses in memory h2 database

## Requirements

For building and running the application you need:

* Java 1.8
* Gradle 7.5
* Docker 2.5 (Optional)

## Build and run with docker(from project root directory)

`./gradlew bootBuildImage --imageName=shiva/purchase-rewards:1.0`

`docker run -p 8080:8080 shiva/purchase-rewards:1.0`

## Build

`./gradlew clean build`

## Run

`./gradlew bootRun`

## Run automated unit and integration tests

`./gradlew test`

## Test rewards API

* Application is pre-populated with following data

```roomsql
insert into purchase_transaction (id, user_id, amount, purchased_on) values (1, 'shiva@test.com', 120, CURRENT_TIMESTAMP());
insert into purchase_transaction (id, user_id, amount, purchased_on) values (2, 'shiva@test.com', 70, {ts '2022-07-17 11:31:52.80'});
insert into purchase_transaction (id, user_id, amount, purchased_on) values (3, 'shiva@test.com', 40, {ts '2022-06-17 14:42:52.49'});
insert into purchase_transaction (id, user_id, amount, purchased_on) values (4, 'shiva@test.com', 140, {ts '2022-03-17 17:57:52.79'});
insert into purchase_transaction (id, user_id, amount, purchased_on) values (5, 'john@test.com', 100, CURRENT_TIMESTAMP());
insert into purchase_transaction (id, user_id, amount, purchased_on) values (6, 'tom@test.com', 49, CURRENT_TIMESTAMP());
insert into purchase_transaction (id, user_id, amount, purchased_on) values (7, 'smith@test.com', 80, {ts '2022-03-20 10:57:43.11'});
```

* Test scenario 1 - Displays rewards for 3 months for customer shiva@test.com

```shell
curl 'http://localhost:8080/rewards/shiva@test.com'
```

```json
{
  "total": 110.0,
  "customer_id": "shiva@test.com",
  "monthly_rewards": [
    {
      "month": "JUNE",
      "total": 0.0
    },
    {
      "month": "JULY",
      "total": 20.0
    },
    {
      "month": "AUGUST",
      "total": 90.0
    }
  ]
}
```

* Test scenario 2 - Displays rewards for 3 months for customer smith@test.com who doesn't have any purchases in last 3
  months

```shell
curl 'http://localhost:8080/rewards/smith@test.com'
```

```json
{
  "total": 0.0,
  "customer_id": "smith@test.com",
  "monthly_rewards": []
}
```

* Test scenario 3 - Displays rewards for 3 months for customer tom@test.com who has purchases in last 3 months but no
  rewards

```shell
curl 'http://localhost:8080/rewards/tom@test.com'
```

```json
{
  "total": 0.0,
  "customer_id": "tom@test.com",
  "monthly_rewards": [
    {
      "month": "AUGUST",
      "total": 0.0
    }
  ]
}
```

* Test scenario 3 - Rewards for customer who doesn't exist

```shell
curl 'http://localhost:8080/rewards/ram@test.com'
```

```json
{
  "status": "BAD_REQUEST",
  "timestamp": "16-08-2022 07:48:29",
  "message": "Customer ram@test.com not found!"
}
```

## The following assumptions have been while developing solution
* Ideally all the customers should be in user table but users are considered directly from the _purchase_transaction_ table
* No timezone is considered while recording the purchase transaction