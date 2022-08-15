create table purchase_transaction
(
    id           int       not null primary key,
    user_id      text      not null,
    amount       double    not null,
    purchased_on timestamp not null
);