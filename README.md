📌 Proje Özeti

Note Summarizer API, kullanıcıların notlarını kaydedip, arka planda yapay zeka ile özetleyen bir REST API uygulamasıdır.
JWT tabanlı kimlik doğrulama, rol tabanlı erişim (ADMIN/AGENT), PostgreSQL veritabanı ve asenkron arka plan görevleri içerir.

🚀 Kullanılan Teknolojiler

Java 17

Spring Boot (Web, Security, Data JPA)

PostgreSQL

JWT Authentication

Asenkron İşlemler (@Async)

Docker (deployment için)

Google Gemini API (özetleme için)

🔑 Temel Özellikler

Kullanıcı kayıt ve giriş (signup/login)

JWT ile güvenli authentication

ADMIN → tüm notlara erişim

AGENT → sadece kendi notlarını görebilir

Not ekleme ve otomatik özetleme

Not durum takibi: QUEUED | PROCESSING | DONE | FAILED
