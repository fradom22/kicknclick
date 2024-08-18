import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {HomepageComponent} from './components/homepage/homepage.component';
import {ModificaUtenteComponent} from './components/modifica-utente/modifica-utente.component';
import {ScarpeGlobaliComponent} from './components/scarpe-globali/scarpe-globali.component';
import {UtentiGlobaliComponent} from './components/utenti-globali/utenti-globali.component';
import {PubblicaScarpaComponent} from "./components/pubblica-scarpa/pubblica-scarpa.component";
import {DettagliScarpaComponent} from "./components/dettagli-scarpa/dettagli-scarpa.component";
import {ScarpePubblicateComponent} from "./components/scarpe-pubblicate/scarpe-pubblicate.component";
import {PropriAcquistiComponent} from "./components/propri-acquisti/propri-acquisti.component";
import {TuttiProdottiComponent} from "./components/tutti-prodotti/tutti-prodotti.component";

const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'homepage', component: HomepageComponent},
  {path: "modifica/:id", component: ModificaUtenteComponent},
  {path: 'scarpeGlobali', component: ScarpeGlobaliComponent},
  {path: 'utentiGlobali', component: UtentiGlobaliComponent},
  {
    path: 'pubblicaScarpa', component: PubblicaScarpaComponent, children: [
      {path: ':id', component: PubblicaScarpaComponent},
    ]
  },
  {path: 'dettagliScarpa/:id', component: DettagliScarpaComponent},
  {path: 'propriAcquisti/:id', component: PropriAcquistiComponent},
  {path: 'scarpePubblicate', component: ScarpePubblicateComponent},
  {path: 'tuttiProdotti', component: TuttiProdottiComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
