create table location
(
    id    int auto_increment
        primary key,
    name  varchar(255)                 not null,
    area  varchar(3)                   not null,
    state varchar(10) default 'active' null,
    constraint name
        unique (name)
);

create table tracking_details
(
    id                  int auto_increment
        primary key,
    tracking_search_id  varchar(255)         null,
    current_location_id int                  not null,
    current_state       varchar(10)          not null,
    picked_up           tinyint(1) default 0 not null,
    processing          tinyint(1) default 0 not null,
    delivered           tinyint(1) default 0 not null,
    constraint tracking_details_location_id_fk
        foreign key (id) references location (id)
);

create table orders
(
    id                   int auto_increment
        primary key,
    order_search_id      varchar(255)               null,
    user_id              int                        null,
    send_user_id         int                        null,
    receipt_user_id      int                        null,
    pickup_location_id   int                        not null,
    delivery_location_id int                        not null,
    created_date         timestamp  default (now()) not null,
    expect_date          timestamp                  null,
    actual_arrival_time  timestamp                  null,
    finished             tinyint(1) default 0       null,
    details_id           int                        null,
    constraint search_id
        unique (order_search_id),
    constraint orders_ibfk_1
        foreign key (details_id) references tracking_details (id)
);

create index delivery_location_id_fk
    on orders (delivery_location_id);

create index details_id
    on orders (details_id);

create index pick_orders___fk
    on orders (pickup_location_id);

create index user_id
    on orders (user_id);

create index search_id
    on tracking_details (tracking_search_id);

create table user
(
    id               int auto_increment
        primary key,
    username         varchar(50)                 not null,
    password         varchar(128)                not null,
    first_name       varchar(255)                not null,
    last_name        varchar(255)                not null,
    email            varchar(255)                not null,
    phone            varchar(255)                not null,
    sex              varchar(1)                  not null,
    role             varchar(20)                 not null,
    created_at       timestamp   default (now()) not null,
    key_question_ans varchar(20) default 'test'  not null,
    constraint email
        unique (email),
    constraint username
        unique (username)
);

