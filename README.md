
# Chefaa - Android coding challenge

A Simple Comics app 

## Instruction to run Project

1: Install Git.
2: Install Android Studio.
3: Clone the project from this url 
## https://github.com/m-abdelsabour/Chefaa-task.git

## API Reference

#### Get comics list

```http
  GET https://gateway.marvel.com/v1/public/comics?ts={timestamp}&apikey={api_key}&hash={md5 for three value}
```

| Parameter | Type     | Description                               |
|:----------| :------- |:------------------------------------------|
| `apiKey`  | `string` | **Required**. Your API key                |
| `ts`      | `string` | **Required**. timeStamp                   |
| `hash`    | `string` | **Required**. md5(ts+privateKey+publicKey)|

## Tech Stack

**Architecture:** MVI, MVVM, Clean Architecture, Modularization by Feature

**Dependency Injection:** Dagger Hilt

**Offline Database:** Room Database

**Threading:** Kotlin Coroutines

**Navigation:** Jetpack Navigation Component, Deep Links


