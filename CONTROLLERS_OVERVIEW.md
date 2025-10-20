# Обзор REST API Контроллеров

## Созданная структура

```
src/main/kotlin/dev/shoqan/fitness_app/
├── controllers/
│   ├── UserController.kt
│   ├── WorkoutController.kt
│   ├── ExerciseLibraryController.kt
│   ├── WorkoutExerciseController.kt
│   └── SetController.kt
├── dto/
│   ├── UserDto.kt
│   ├── WorkoutDto.kt
│   ├── ExerciseLibraryDto.kt
│   ├── WorkoutExerciseDto.kt
│   └── SetDto.kt
└── mappers/
    └── EntityMappers.kt
```

---

## 1. UserController (`/api/users`)

**Функции:**
- ✅ CRUD операции для пользователей
- ✅ Поиск по username
- ✅ Проверка существования
- ✅ Валидация уникальности username

**Endpoints:**
| Метод | Путь | Описание |
|-------|------|----------|
| GET | `/api/users` | Все пользователи |
| GET | `/api/users/{id}` | По ID |
| GET | `/api/users/username/{username}` | По username |
| POST | `/api/users` | Создать |
| PUT | `/api/users/{id}` | Обновить |
| DELETE | `/api/users/{id}` | Удалить |
| GET | `/api/users/{id}/exists` | Проверка существования |

**Особенности:**
- Проверка конфликтов username при создании/обновлении
- TODO: Нужно добавить хеширование паролей (Spring Security)

---

## 2. WorkoutController (`/api/workouts`)

**Функции:**
- ✅ CRUD операции для тренировок
- ✅ Фильтрация по пользователю
- ✅ Фильтрация по диапазону дат
- ✅ Summary и детальные представления
- ✅ Автоматический расчет метрик

**Endpoints:**
| Метод | Путь | Описание |
|-------|------|----------|
| GET | `/api/workouts` | Все (summary) |
| GET | `/api/workouts/{id}` | По ID (full) |
| GET | `/api/workouts/user/{userId}` | По пользователю |
| GET | `/api/workouts/user/{userId}/date-range` | За период |
| GET | `/api/workouts/{id}/summary` | Summary |
| POST | `/api/workouts` | Создать |
| PUT | `/api/workouts/{id}` | Обновить |
| DELETE | `/api/workouts/{id}` | Удалить |

**Особенности:**
- Query параметр `?summary=true/false` для выбора формата
- Сортировка по дате (новые первые) для списков пользователя
- Валидация существования user при создании

**Response типы:**
- `WorkoutResponse` - полная информация с упражнениями
- `WorkoutSummaryResponse` - краткая информация с метриками

---

## 3. ExerciseLibraryController (`/api/exercises`)

**Функции:**
- ✅ CRUD операции для библиотеки упражнений
- ✅ Умный поиск (встроенные → свои → публичные)
- ✅ Фильтрация по группе мышц
- ✅ Разделение встроенных и пользовательских
- ✅ Валидация уникальности имени

**Endpoints:**
| Метод | Путь | Описание |
|-------|------|----------|
| GET | `/api/exercises` | Все упражнения |
| GET | `/api/exercises/{id}` | По ID |
| GET | `/api/exercises/search` | Умный поиск |
| GET | `/api/exercises/muscle-group/{group}` | По группе мышц |
| GET | `/api/exercises/built-in` | Встроенные |
| GET | `/api/exercises/user/{userId}/custom` | Пользовательские |
| GET | `/api/exercises/name/{name}` | По имени |
| POST | `/api/exercises` | Создать |
| PUT | `/api/exercises/{id}` | Обновить |
| DELETE | `/api/exercises/{id}` | Удалить |

**Особенности:**
- **Smart Search** (`/search?userId={id}&query={text}`):
  - Приоритизация: built-in → user's custom → public custom
  - Case-insensitive partial matching
  - Требует userId для правильной фильтрации
- Проверка конфликтов имени (case-insensitive)
- Автоматическая установка `isCustom=true` если указан `createdById`

---

## 4. WorkoutExerciseController (`/api/workout-exercises`)

**Функции:**
- ✅ CRUD для упражнений в тренировке
- ✅ Связь тренировки и библиотеки упражнений
- ✅ Управление порядком (orderIndex)
- ✅ Метрики упражнения (volume, reps, set count)

**Endpoints:**
| Метод | Путь | Описание |
|-------|------|----------|
| GET | `/api/workout-exercises/{id}` | По ID |
| GET | `/api/workout-exercises/workout/{workoutId}` | Упражнения тренировки |
| GET | `/api/workout-exercises/exercise-library/{exerciseLibraryId}` | История использования |
| GET | `/api/workout-exercises/{id}/metrics` | Метрики |
| POST | `/api/workout-exercises` | Добавить в тренировку |
| PUT | `/api/workout-exercises/{id}` | Обновить |
| DELETE | `/api/workout-exercises/{id}` | Удалить |

**Особенности:**
- Валидация существования workout и exerciseLibrary
- Автоматическая сортировка по orderIndex
- Метрики считаются на лету из sets
- Каскадное удаление всех sets при удалении

**Метрики endpoint:**
```json
{
  "totalVolume": 1000.0,
  "totalReps": 15,
  "setCount": 3
}
```

---

## 5. SetController (`/api/sets`)

**Функции:**
- ✅ CRUD для подходов
- ✅ Bulk создание нескольких подходов
- ✅ Управление порядком (orderIndex)
- ✅ Batch удаление всех подходов упражнения

**Endpoints:**
| Метод | Путь | Описание |
|-------|------|----------|
| GET | `/api/sets/{id}` | По ID |
| GET | `/api/sets/workout-exercise/{workoutExerciseId}` | Подходы упражнения |
| POST | `/api/sets` | Создать один |
| POST | `/api/sets/bulk` | Создать несколько |
| PUT | `/api/sets/{id}` | Обновить |
| DELETE | `/api/sets/{id}` | Удалить один |
| DELETE | `/api/sets/workout-exercise/{workoutExerciseId}` | Удалить все |

**Особенности:**
- **Bulk endpoint** для эффективного создания нескольких подходов
- Автоматическая установка orderIndex если не указан (при bulk)
- Автоматическая сортировка по orderIndex
- Batch delete возвращает количество удаленных

---

## Архитектурные решения

### 1. **DTO Pattern**
- ✅ Разделение Request и Response DTOs
- ✅ Валидация на уровне DTO (`@Valid`, `@NotBlank`, `@Min`)
- ✅ Nullable поля в Update requests (частичное обновление)

### 2. **Mapper Functions**
- ✅ Extension functions для конвертации Entity → Response
- ✅ Опциональная загрузка вложенных коллекций
- ✅ Простой и читаемый код маппинга

### 3. **Response Entities**
- ✅ Правильные HTTP коды:
  - `200 OK` - успешное получение/обновление
  - `201 Created` - создание ресурса
  - `204 No Content` - успешное удаление
  - `400 Bad Request` - неверные данные
  - `404 Not Found` - ресурс не найден
  - `409 Conflict` - конфликт уникальности

### 4. **Validation**
- ✅ Bean Validation на уровне DTO
- ✅ Бизнес-логика валидация в контроллерах
- ✅ Проверка существования связанных сущностей
- ✅ Проверка конфликтов уникальности

### 5. **Query Optimization**
- ✅ Lazy loading для избежания N+1
- ✅ Опциональная загрузка коллекций
- ✅ Custom queries для сложных фильтраций
- ✅ Индексированные запросы (через JPA methods)

---

## Best Practices реализованные

### ✅ REST Conventions
- Существительные в множественном числе для коллекций
- Вложенные ресурсы через путь (`/user/{userId}/custom`)
- Query parameters для фильтрации и опций

### ✅ Error Handling
- Правильные HTTP статусы
- Проверка существования перед операциями
- Валидация связанных ресурсов

### ✅ Consistency
- Единый стиль именования
- Согласованные структуры Response
- Стандартная валидация

### ✅ Performance
- Опциональная загрузка (summary vs full)
- Bulk operations где уместно
- Эффективные query methods

---

## TODO / Будущие улучшения

### Security
- [ ] Добавить Spring Security
- [ ] JWT authentication
- [ ] Хеширование паролей (BCrypt)
- [ ] Authorization проверки (пользователь может редактировать только свои данные)

### Error Handling
- [ ] Global exception handler (`@ControllerAdvice`)
- [ ] Стандартизированные error responses
- [ ] Детальные сообщения ошибок валидации

### Advanced Features
- [ ] Pagination для больших списков
- [ ] Sorting параметры для списков
- [ ] Advanced filtering (multiple parameters)
- [ ] Rate limiting
- [ ] API versioning

### Documentation
- [ ] Swagger/OpenAPI интеграция
- [ ] API documentation auto-generation
- [ ] Request/Response примеры

### Testing
- [ ] Unit tests для контроллеров
- [ ] Integration tests
- [ ] Test data fixtures

---

## Как запустить

1. Убедитесь, что PostgreSQL запущен
2. Обновите `application.properties` с вашими настройками БД
3. Запустите приложение:
```bash
./gradlew bootRun
```

4. API доступен на: `http://localhost:8080/api`

5. Проверьте работоспособность:
```bash
# Health check
curl http://localhost:8080/actuator/health

# Создать пользователя
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"test123"}'
```

---

## Документация

Смотрите `API_EXAMPLES.md` для детальных примеров использования каждого endpoint с curl командами и полными примерами request/response.