# 📚 Library Management System

Ushbu loyiha **Jakarta EE** texnologiyalari asosida yozilgan va foydalanuvchilarga kutubxona kitoblarini boshqarish imkonini beradi. Loyiha ta'limiy maqsadlarda yaratilgan va asosiy CRUD funksiyalarni o'z ichiga oladi.

---

## 🛠 Texnologiyalar va kutubxonalar

- **Jakarta EE 10**
- **Tomcat 10** — Java Servlet konteyneri sifatida ishlatilgan
- **Jakarta Servlet API**
- **JSP & JSTL** — View qatlam uchun
- **Hibernate ORM** — Ma'lumotlar bazasi bilan ishlash
- **Jakarta Persistence API (JPA)** — Entitylar orqali bazani boshqarish
- **Bean Validation (Jakarta Validation)** — Foydalanuvchi kiritgan ma'lumotlarni tekshirish

---

## 📁 Loyiha tuzilmasi

```plaintext
library/
├── src/
│   └── main/
│       ├── java/           # Controller, Service, Model va DAO qatlamlari
│       └── webapp/
│           ├── WEB-INF/    # web.xml konfiguratsiyasi
│           └── views/      # JSP fayllar
├── pom.xml                 # Maven dependency fayli
