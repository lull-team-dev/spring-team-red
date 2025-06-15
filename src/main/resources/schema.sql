Drop table IF EXISTS users CASCADE;
Drop table IF EXISTS hotels CASCADE;
Drop table IF EXISTS plans CASCADE;
Drop table IF EXISTS reservations;

-- ユーザーテーブル
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    last_name TEXT NOT NULL,
    first_name TEXT NOT NULL,
    address TEXT NOT NULL,
    tel TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

-- ホテルテーブル
CREATE TABLE hotels (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    photo TEXT,
    prefecture TEXT NOT NULL,
    city TEXT NOT NULL
);

-- プランテーブル（各ホテルに紐づく宿泊プラン）
CREATE TABLE plans (
    id SERIAL PRIMARY KEY,
    hotel_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    price INTEGER NOT NULL,
    eat TEXT NOT NULL,
    description TEXT NOT NULL,
    plan_image TEXT NOT NULL,
    CONSTRAINT fk_plan_hotel FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE
);

-- 予約テーブル（プランを含む）
CREATE TABLE reservations (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    plan_id INTEGER NOT NULL,
    number_of_people INTEGER NOT NULL,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    pay TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reservation_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_reservation_plan FOREIGN KEY (plan_id) REFERENCES plans(id) ON DELETE CASCADE
);
