# ⚽ One Badge

For fans who live and breathe their team. OneBadge keeps you connected with live scores, match news, and stats - all in one place!

---

## 📌 Version Roadmap

### **v0.1.0 – Project Skeleton & Basic UI**
**Goal:** App opens, shows static swipe cards with placeholder content.  
**Tasks:**
- [ ] Build **Swipe Card layout** (Bumble-style) with hardcoded dummy data
- [ ] Implement basic swipe animations
- [ ] Create placeholder card types:
  - [ ] Next Match
  - [ ] Last Match
  - [ ] Top Scorer
  - [ ] League Standing
  - [ ] Fun Fact
- [ ] Navigation setup (Splash → Home)

---

### **v0.2.0 – API Integration (Read-Only)**
**Goal:** Replace static data with live data from API.  
**Tasks:**
- [ ] Choose API (TheSportsDB or API-Football free tier)
- [ ] Setup **Retrofit** (or Ktor Client)
- [ ] Fetch:
  - [ ] List of teams
  - [ ] Next match for a team
  - [ ] Last match result
  - [ ] Top scorer stats
  - [ ] League standings
- [ ] Display real API data in cards

---

### **v0.3.0 – Team Selection & Data Binding**
**Goal:** User chooses their team → cards show relevant data.  
**Tasks:**
- [ ] Add **Onboarding/Team Selection** screen with search
- [ ] Save team ID in **DataStore**
- [ ] Bind cards to selected team
- [ ] Pull-to-refresh on Home
- [ ] Light/dark mode support (static)

---

### **v0.4.0 – UI Polish & Dynamic Themes**
**Goal:** Smooth, sexy UI that feels premium.  
**Tasks:**
- [ ] Replace default splash with animated Compose splash screen
- [ ] Gradient backgrounds in team colors
- [ ] Card animations (scale, rotation, bounce)
- [ ] Live countdown timer for next match
- [ ] Shimmer loading effect
- [ ] Looping card stack

---

### **v0.5.0 – Offline & Error Handling**
**Goal:** App works even without internet.  
**Tasks:**
- [ ] Cache last API data (Room or DataStore)
- [ ] Show cached data if offline
- [ ] Error states (Retry, “No data”)
- [ ] API rate limit handling

---

### **v0.6.0 – Extra UX Goodies**
**Goal:** Delight the user with extra touches.  
**Tasks:**
- [ ] Lottie animations on match win/loss
- [ ] Share card as image/text
- [ ] Swipe haptic feedback
- [ ] Animated transitions between cards

---

### **v1.0.0 – Public Release**
**Goal:** Ready for Indus Appstore submission.  
**Tasks:**
- [ ] Settings screen:
  - [ ] Change favourite team
  - [ ] Toggle theme
  - [ ] About app
- [ ] Test on multiple screen sizes
- [ ] App icon & branding
- [ ] Final performance tweaks
- [ ] Submit to Indus Appstore

---

