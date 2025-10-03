# ⚽ One Badge

For fans who live and breathe their team. OneBadge keeps you connected with live scores, match news, and stats - all in one place!

---

## 📌 Version Roadmap

### **v0.1.0 – Project Skeleton & Basic UI**
**Goal:** App opens, shows static swipe cards with placeholder content.  
**Tasks:**
- [x] Create HomeScreen
- [x] Build **Swipe Card layout** with hardcoded dummy data
- [x] Implement basic swipe animations
- [x] Create placeholder card types:
  - [x] Info 
  - [x] Next Match
  - [x] Last Match
  - [x] Top Scorer
  - [x] League Standing

---

### **v0.2.0 – API Integration (Read-Only)**
**Goal:** Replace static data with live data from API.  
**Tasks:**
- [x] Choose API (TheSportsDB or API-Football free tier)
- [x] Setup **Retrofit** (or Ktor Client)
- [x] Display real API data in cards

---

### **v0.3.0 – Team Selection & Data Binding**
**Goal:** User chooses their team → cards show relevant data.  
**Tasks:**
- [x] Add **Onboarding/Team Selection** screen with search
- [x] Save team ID in **DataStore**
- [x] Bind cards to selected team

---

### **v0.4.0 – UI Polish & Dynamic Themes**
**Goal:** Smooth, sexy UI that feels premium.  
**Tasks:**
- [x] Replace default splash with animated Compose splash screen
- [x] Gradient backgrounds in team colors
- [x] Light/dark mode support (static)

---

### **v0.5.0 – Offline & Error Handling**
**Goal:** App works even without internet.  
**Tasks:**
- [ ] Cache last API data (Room or DataStore)
- [ ] Show cached data if offline
- [ ] Error states (Retry, “No data”)
- [ ] API rate limit handling
- [ ] Pull-to-refresh on Home

---

### **v0.6.0 – Extra UX Goodies**
**Goal:** Delight the user with extra touches.  
**Tasks:**
- [ ] Share card as image/text
- [ ] Animated transitions between cards
- [ ] Card animations (scale, rotation, bounce)
- [ ] Live countdown timer for next match
- [ ] Shimmer loading effect
- [ ] Looping card stack

---

### **v1.0.0 – Public Release**
**Goal:** Ready for Indus Appstore submission.  
**Tasks:**
- [ ] Strings
- [ ] Settings screen:
  - [ ] Change favourite team
  - [ ] Toggle theme
  - [ ] About app
- [ ] Test on multiple screen sizes
- [ ] App icon & branding
- [ ] Final performance tweaks
- [ ] Submit to Indus Appstore

---

