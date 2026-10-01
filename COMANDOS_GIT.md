# Comandos Git para el laboratorio (subir, crear ramas, cambiar de rama)

Guía genérica basada en el patrón que piden los labs/exámenes del curso:
`main` vacía, `develop` como base real, y una rama nueva por cada
pregunta/feature, creada a partir de la rama anterior.

## 1. Preparar el repositorio local (una sola vez)

```bash
cd lab-estudio
git init
git add .
git commit -m "Version inicial del proyecto"
```

## 2. Conectar con el repositorio remoto (GitHub)

Crea primero el repositorio vacío en GitHub (sin README, sin .gitignore) y copia su URL.

```bash
git remote add origin https://github.com/TU-USUARIO/TU-REPO.git
git branch -M main
git push -u origin main
```

- `git remote add origin <url>` → vincula tu carpeta local con el repo remoto.
- `git branch -M main` → asegura que tu rama principal se llame `main`.
- `git push -u origin main` → sube la rama y la deja "trackeada" (los próximos `git push` ya no necesitan `-u origin main`).

## 3. Crear la rama `develop` (main debe quedar vacía)

Muchos casos piden que `main` esté siempre vacía y que todo el trabajo viva en `develop` y sus ramas hijas.

```bash
git checkout -b develop
# (si main ya tiene archivos y se supone que debía estar vacía, bórralos en esta rama
#  o simplemente no vuelvas a hacer commits sobre main después de este punto)
git push -u origin develop
```

## 4. Crear una rama nueva A PARTIR DE otra rama (patrón feature)

Regla general: siempre haz `checkout` a la rama base PRIMERO, y desde ahí creas la nueva.

```bash
git checkout develop              # me paro en la rama base
git checkout -b navbar            # creo y me muevo a "navbar" (sale de develop)
# ... trabajas, programas el navbar con fragmentos ...
git add .
git commit -m "Navbar con fragmentos Thymeleaf"
git push -u origin navbar
```

Para la siguiente rama, que sale de `navbar` (no de `develop`):

```bash
git checkout navbar               # me aseguro de estar en la rama base correcta
git checkout -b customer          # nueva rama a partir de "navbar"
# ... CRUD de clientes ...
git add .
git commit -m "CRUD de clientes con validaciones"
git push -u origin customer
```

Y así sucesivamente (`product` a partir de `customer`, `invoice` a partir de `product`, etc.):

```bash
git checkout customer
git checkout -b product
git add .
git commit -m "CRUD de productos con validaciones"
git push -u origin product

git checkout product
git checkout -b invoice
git add .
git commit -m "Emision de comprobantes"
git push -u origin invoice
```

## 5. Cambiar de rama (sin crear una nueva)

```bash
git checkout nombre-rama
# o, forma moderna equivalente:
git switch nombre-rama
```

Ver en qué rama estás parado y qué ramas existen:

```bash
git branch          # ramas locales (la actual tiene un *)
git branch -a       # locales + remotas
```

## 6. Subir cambios normales (ya en una rama existente)

```bash
git add .                          # o git add archivo-especifico
git commit -m "Mensaje descriptivo del cambio"
git push                           # si la rama ya esta trackeada con -u
```

## 7. Ver el estado y el historial (para revisar antes de subir)

```bash
git status          # que archivos estan modificados/sin trackear
git diff             # que cambió exactamente
git log --oneline --graph --all   # historial de commits y ramas
```

## 8. Errores comunes que penalizan en los labs (ojo con esto)

- **"Si se entrega un proyecto con errores al compilar es -3p"** → antes de hacer el commit final, corre `mvn clean compile` (o `mvn spring-boot:run`) y confirma que no hay errores.
- **"Tiene que subir el repositorio git en caso no lo haga -3p"** → no olvides el `git push` de CADA rama que crees, no solo de la última.
- **"Si no se respeta la creación de las ramas, no se revisará esa pregunta"** → respeta el orden exacto que pide el enunciado (de qué rama sale cada rama nueva); revísalo con `git log --graph --all --oneline` antes de entregar.
- Antes de crear una rama nueva, verifica con `git branch` que estás parado en la rama base correcta — crear la rama desde el lugar equivocado es el error más común.

## 9. Resumen rápido (cheatsheet)

| Acción | Comando |
| --- | --- |
| Iniciar repo | `git init` |
| Conectar remoto | `git remote add origin <url>` |
| Subir por primera vez | `git push -u origin <rama>` |
| Crear rama desde la actual | `git checkout -b <nueva-rama>` |
| Cambiar de rama | `git checkout <rama>` o `git switch <rama>` |
| Ver ramas | `git branch -a` |
| Guardar cambios | `git add . && git commit -m "mensaje"` |
| Subir cambios | `git push` |
| Ver historial de ramas | `git log --oneline --graph --all` |
