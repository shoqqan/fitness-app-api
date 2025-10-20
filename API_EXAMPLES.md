# Fitness App REST API - Примеры использования

## Обзор
Все endpoints начинаются с `/api`

## 1. User API (`/api/users`)

### Создать пользователя
```bash
POST /api/users
Content-Type: application/json

{
  "username": "john_doe",
  "password": "securepassword123"
}

# Response: 201 Created
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "username": "john_doe",
  "createdAt": "2024-01-20T10:00:00Z",
  "updatedAt": "2024-01-20T10:00:00Z"
}
```

### Получить всех пользователей
```bash
GET /api/users

# Response: 200 OK
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "username": "john_doe",
    "createdAt": "2024-01-20T10:00:00Z",
    "updatedAt": "2024-01-20T10:00:00Z"
  }
]
```

### Получить пользователя по ID
```bash
GET /api/users/{id}

# Response: 200 OK или 404 Not Found
```

### Получить пользователя по username
```bash
GET /api/users/username/john_doe

# Response: 200 OK или 404 Not Found
```

### Обновить пользователя
```bash
PUT /api/users/{id}
Content-Type: application/json

{
  "username": "john_updated",
  "password": "newpassword123"
}

# Response: 200 OK
```

### Удалить пользователя
```bash
DELETE /api/users/{id}

# Response: 204 No Content
```

### Проверить существование пользователя
```bash
GET /api/users/{id}/exists

# Response: 200 OK
{
  "exists": true
}
```

---

## 2. Exercise Library API (`/api/exercises`)

### Создать упражнение
```bash
POST /api/exercises
Content-Type: application/json

{
  "name": "Bench Press",
  "description": "Chest exercise using barbell",
  "muscleGroup": "Chest",
  "category": "Compound",
  "isPublic": true,
  "createdById": "123e4567-e89b-12d3-a456-426614174000"
}

# Response: 201 Created
{
  "id": "abc12345-...",
  "name": "Bench Press",
  "description": "Chest exercise using barbell",
  "muscleGroup": "Chest",
  "category": "Compound",
  "isCustom": true,
  "isPublic": true,
  "createdById": "123e4567-...",
  "createdAt": "2024-01-20T10:00:00Z",
  "updatedAt": "2024-01-20T10:00:00Z"
}
```

### Поиск упражнений (умный поиск)
```bash
GET /api/exercises/search?userId={userId}&query=bench

# Приоритет: встроенные -> свои -> публичные
# Response: 200 OK
[
  {
    "id": "...",
    "name": "Bench Press",
    "muscleGroup": "Chest",
    ...
  }
]
```

### Получить упражнения по группе мышц
```bash
GET /api/exercises/muscle-group/Chest

# Response: 200 OK
```

### Получить встроенные упражнения
```bash
GET /api/exercises/built-in

# Response: 200 OK
```

### Получить пользовательские упражнения
```bash
GET /api/exercises/user/{userId}/custom

# Response: 200 OK
```

### Найти упражнение по имени
```bash
GET /api/exercises/name/Bench%20Press

# Response: 200 OK или 404 Not Found
```

### Обновить упражнение
```bash
PUT /api/exercises/{id}
Content-Type: application/json

{
  "description": "Updated description",
  "isPublic": false
}

# Response: 200 OK
```

### Удалить упражнение
```bash
DELETE /api/exercises/{id}

# Response: 204 No Content
```

---

## 3. Workout API (`/api/workouts`)

### Создать тренировку
```bash
POST /api/workouts
Content-Type: application/json

{
  "title": "Monday Chest Day",
  "date": "2024-01-20T18:00:00Z",
  "userId": "123e4567-e89b-12d3-a456-426614174000"
}

# Response: 201 Created
{
  "id": "workout-uuid",
  "title": "Monday Chest Day",
  "date": "2024-01-20T18:00:00Z",
  "userId": "123e4567-...",
  "exercises": [],
  "createdAt": "2024-01-20T18:00:00Z",
  "updatedAt": "2024-01-20T18:00:00Z"
}
```

### Получить все тренировки (summary)
```bash
GET /api/workouts

# Response: 200 OK
[
  {
    "id": "workout-uuid",
    "title": "Monday Chest Day",
    "date": "2024-01-20T18:00:00Z",
    "userId": "123e4567-...",
    "exerciseCount": 3,
    "totalVolume": 2500.0,
    "totalReps": 45,
    "createdAt": "2024-01-20T18:00:00Z"
  }
]
```

### Получить тренировку по ID (с упражнениями)
```bash
GET /api/workouts/{id}

# Response: 200 OK
{
  "id": "workout-uuid",
  "title": "Monday Chest Day",
  "date": "2024-01-20T18:00:00Z",
  "userId": "123e4567-...",
  "exercises": [
    {
      "id": "exercise-uuid",
      "exerciseName": "Bench Press",
      "restSeconds": 90,
      "orderIndex": 0,
      "sets": [...],
      "totalVolume": 1000.0,
      "totalReps": 15
    }
  ],
  "createdAt": "2024-01-20T18:00:00Z",
  "updatedAt": "2024-01-20T18:00:00Z"
}
```

### Получить тренировки пользователя
```bash
# Summary версия
GET /api/workouts/user/{userId}?summary=true

# Полная версия с упражнениями
GET /api/workouts/user/{userId}?summary=false

# Response: 200 OK (отсортировано по дате, новые первые)
```

### Получить тренировки за период
```bash
GET /api/workouts/user/{userId}/date-range?startDate=2024-01-01T00:00:00Z&endDate=2024-01-31T23:59:59Z

# Response: 200 OK
```

### Получить summary тренировки
```bash
GET /api/workouts/{id}/summary

# Response: 200 OK
{
  "id": "workout-uuid",
  "title": "Monday Chest Day",
  "date": "2024-01-20T18:00:00Z",
  "userId": "123e4567-...",
  "exerciseCount": 3,
  "totalVolume": 2500.0,
  "totalReps": 45,
  "createdAt": "2024-01-20T18:00:00Z"
}
```

### Обновить тренировку
```bash
PUT /api/workouts/{id}
Content-Type: application/json

{
  "title": "Updated Chest Day"
}

# Response: 200 OK
```

### Удалить тренировку
```bash
DELETE /api/workouts/{id}

# Response: 204 No Content
# Все упражнения и подходы каскадно удалятся
```

---

## 4. Workout Exercise API (`/api/workout-exercises`)

### Добавить упражнение в тренировку
```bash
POST /api/workout-exercises
Content-Type: application/json

{
  "workoutId": "workout-uuid",
  "exerciseLibraryId": "exercise-lib-uuid",
  "restSeconds": 90,
  "orderIndex": 0
}

# Response: 201 Created
{
  "id": "workout-exercise-uuid",
  "workoutId": "workout-uuid",
  "exerciseLibraryId": "exercise-lib-uuid",
  "exerciseName": "Bench Press",
  "restSeconds": 90,
  "orderIndex": 0,
  "sets": [],
  "totalVolume": 0.0,
  "totalReps": 0,
  "createdAt": "2024-01-20T18:00:00Z",
  "updatedAt": "2024-01-20T18:00:00Z"
}
```

### Получить упражнения тренировки
```bash
GET /api/workout-exercises/workout/{workoutId}

# Response: 200 OK (отсортировано по orderIndex)
```

### Получить где использовалось упражнение
```bash
GET /api/workout-exercises/exercise-library/{exerciseLibraryId}

# Response: 200 OK
```

### Получить метрики упражнения
```bash
GET /api/workout-exercises/{id}/metrics

# Response: 200 OK
{
  "totalVolume": 1000.0,
  "totalReps": 15,
  "setCount": 3
}
```

### Обновить упражнение в тренировке
```bash
PUT /api/workout-exercises/{id}
Content-Type: application/json

{
  "restSeconds": 120,
  "orderIndex": 1
}

# Response: 200 OK
```

### Удалить упражнение из тренировки
```bash
DELETE /api/workout-exercises/{id}

# Response: 204 No Content
# Все подходы каскадно удалятся
```

---

## 5. Set API (`/api/sets`)

### Добавить подход
```bash
POST /api/sets
Content-Type: application/json

{
  "workoutExerciseId": "workout-exercise-uuid",
  "weight": 100.0,
  "reps": 10,
  "orderIndex": 0
}

# Response: 201 Created
{
  "id": "set-uuid",
  "workoutExerciseId": "workout-exercise-uuid",
  "weight": 100.0,
  "reps": 10,
  "orderIndex": 0,
  "createdAt": "2024-01-20T18:00:00Z",
  "updatedAt": "2024-01-20T18:00:00Z"
}
```

### Добавить несколько подходов одновременно
```bash
POST /api/sets/bulk
Content-Type: application/json

[
  {
    "workoutExerciseId": "workout-exercise-uuid",
    "weight": 100.0,
    "reps": 10,
    "orderIndex": 0
  },
  {
    "workoutExerciseId": "workout-exercise-uuid",
    "weight": 100.0,
    "reps": 8,
    "orderIndex": 1
  },
  {
    "workoutExerciseId": "workout-exercise-uuid",
    "weight": 100.0,
    "reps": 6,
    "orderIndex": 2
  }
]

# Response: 201 Created
[...]
```

### Получить подходы упражнения
```bash
GET /api/sets/workout-exercise/{workoutExerciseId}

# Response: 200 OK (отсортировано по orderIndex)
```

### Обновить подход
```bash
PUT /api/sets/{id}
Content-Type: application/json

{
  "weight": 105.0,
  "reps": 12
}

# Response: 200 OK
```

### Удалить подход
```bash
DELETE /api/sets/{id}

# Response: 204 No Content
```

### Удалить все подходы упражнения
```bash
DELETE /api/sets/workout-exercise/{workoutExerciseId}

# Response: 200 OK
{
  "deletedCount": 3
}
```

---

## Типичный флоу использования

### 1. Регистрация пользователя
```bash
POST /api/users
{
  "username": "athlete1",
  "password": "pass123"
}
# Получаем userId
```

### 2. Поиск упражнений
```bash
GET /api/exercises/search?userId={userId}&query=bench
# Выбираем exerciseLibraryId
```

### 3. Создание тренировки
```bash
POST /api/workouts
{
  "title": "Push Day",
  "date": "2024-01-20T18:00:00Z",
  "userId": "{userId}"
}
# Получаем workoutId
```

### 4. Добавление упражнений в тренировку
```bash
POST /api/workout-exercises
{
  "workoutId": "{workoutId}",
  "exerciseLibraryId": "{exerciseLibraryId}",
  "restSeconds": 90,
  "orderIndex": 0
}
# Получаем workoutExerciseId
```

### 5. Запись подходов
```bash
POST /api/sets/bulk
[
  {
    "workoutExerciseId": "{workoutExerciseId}",
    "weight": 100.0,
    "reps": 10,
    "orderIndex": 0
  },
  {
    "workoutExerciseId": "{workoutExerciseId}",
    "weight": 100.0,
    "reps": 8,
    "orderIndex": 1
  }
]
```

### 6. Просмотр завершенной тренировки
```bash
GET /api/workouts/{workoutId}
# Получаем полную информацию с упражнениями и подходами
```

### 7. Анализ прогресса
```bash
GET /api/workouts/user/{userId}/date-range?startDate=...&endDate=...
# Получаем все тренировки за период с метриками
```

---

## Коды ответов

- `200 OK` - Успешный запрос
- `201 Created` - Ресурс создан
- `204 No Content` - Успешное удаление
- `400 Bad Request` - Неверные данные или связанный ресурс не найден
- `404 Not Found` - Ресурс не найден
- `409 Conflict` - Конфликт (username или exercise name уже существует)

---

## Валидация

### User
- `username`: 3-50 символов, обязательно, уникально
- `password`: 6-100 символов, обязательно

### Exercise
- `name`: обязательно, уникально (case-insensitive)

### Workout
- `title`: обязательно
- `date`: обязательно
- `userId`: обязательно, должен существовать

### WorkoutExercise
- `workoutId`: обязательно, должен существовать
- `exerciseLibraryId`: обязательно, должен существовать
- `restSeconds`: >= 0
- `orderIndex`: >= 0

### Set
- `workoutExerciseId`: обязательно, должен существовать
- `weight`: >= 0
- `reps`: >= 0
- `orderIndex`: >= 0

---

## Важные заметки

1. **Cascading Deletes**: При удалении workout удаляются все связанные exercises и sets
2. **Password Security**: В текущей версии пароли НЕ хешируются - нужно добавить Spring Security
3. **Sorting**:
   - Workouts сортируются по дате (новые первые)
   - Exercises в workout по orderIndex
   - Sets в exercise по orderIndex
4. **Smart Search**: Поиск упражнений показывает встроенные первыми, потом свои, потом публичные
5. **UUID**: Все ID используют UUID формат
6. **Timestamps**: Все даты в формате ISO 8601 с timezone (OffsetDateTime)