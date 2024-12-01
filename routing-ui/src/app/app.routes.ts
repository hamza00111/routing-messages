import { Routes } from '@angular/router';
import {MessageListComponent} from "./messages/message-list/message-list.component";

export const routes: Routes = [
  { path: '', redirectTo: '/messages', pathMatch: 'full' },
  { path: 'messages', component: MessageListComponent },
];
