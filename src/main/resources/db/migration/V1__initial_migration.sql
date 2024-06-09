create table if not exists user
(
    user_id     varchar(255) not null,
    first_name  varchar(255) null,
    last_name   varchar(255) null,
    email       varchar(255) not null unique,
    password    varchar(255) not null,
    role        varchar(255) null,
    user_status boolean      null,
    created_at  timestamp    null,
    created_by  varchar(255) null,
    updated_at  timestamp    null,
    updated_by  varchar(255) null,
    constraint user_pk
        primary key (user_id)
);

create table if not exists product
(
    id              varchar(255) not null,
    product_name    varchar(255) null,
    price           float null,
    product_status       boolean null,
    created_at  timestamp    null,
    created_by  varchar(255) null,
    updated_at  timestamp    null,
    updated_by  varchar(255) null,
    constraint user_pk
        primary key (id)
);


