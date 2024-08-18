import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { FormsModule } from '@angular/forms';
import { ScarpeGlobaliComponent } from './components/scarpe-globali/scarpe-globali.component';
import { ModificaUtenteComponent } from './components/modifica-utente/modifica-utente.component';
import { UtentiGlobaliComponent } from './components/utenti-globali/utenti-globali.component';
import { PubblicaScarpaComponent } from './components/pubblica-scarpa/pubblica-scarpa.component';
import { DettagliScarpaComponent } from './components/dettagli-scarpa/dettagli-scarpa.component';
import { ScarpePubblicateComponent } from './components/scarpe-pubblicate/scarpe-pubblicate.component';
import { PropriAcquistiComponent } from './components/propri-acquisti/propri-acquisti.component';
import { TuttiProdottiComponent } from './components/tutti-prodotti/tutti-prodotti.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomepageComponent,
    ScarpeGlobaliComponent,
    ModificaUtenteComponent,
    UtentiGlobaliComponent,
    PubblicaScarpaComponent,
    DettagliScarpaComponent,
    ScarpePubblicateComponent,
    PropriAcquistiComponent,
    TuttiProdottiComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
