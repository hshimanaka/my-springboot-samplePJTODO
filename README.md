# TODO アプリ

Spring Boot + MyBatis + PostgreSQL で作成した、ユーザーごとのタスク管理アプリです。

## 動作環境
- JDK 25.0.3
- PostgreSQL 18.3

## セットアップ

### 1. データベースとロールを作成する
```sql
CREATE DATABASE todo_app;
CREATE ROLE todo_app WITH LOGIN PASSWORD '任意のパスワード';
GRANT ALL PRIVILEGES ON DATABASE todo_app TO todo_app;
```

### 2. 環境変数を設定する
```bash
export DB_USER=todo_app
export DB_PASSWORD=手順1で設定したパスワード
```

### 3. 起動する
```bash
./gradlew bootRun
```

テーブル(`login` / `tasks`)は起動時に`src/main/resources/schema.sql`から自動作成されます。

http://localhost:8080/ にアクセスしてください。

## 画面
| URL | 内容 |
|-----|------|
| `/` | トップページ |
| `/register` | ユーザー登録 |
| `/login` | ログイン |
| `/tasks` | タスク一覧（要ログイン） |
