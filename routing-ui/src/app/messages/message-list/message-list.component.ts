import {Component, OnInit, ViewChild} from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MessageService } from '../services/message.service';
import { Message } from '../models/message.model';
import { MessageDetailComponent } from '../message-detail/message-detail.component';
import { LoadingSpinnerComponent } from '../../shared/components/loading-spinner/loading-spinner.component';
import { ErrorMessageComponent } from '../../shared/components/error-message/error-message.component';
import { formatDate } from '../../core/utils/date.utils';
import {MatPaginator, MatPaginatorModule, PageEvent} from "@angular/material/paginator";
import {MatIconModule} from "@angular/material/icon";

@Component({
  selector: 'app-message-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    LoadingSpinnerComponent,
    ErrorMessageComponent,
    MatPaginatorModule,
    MatIconModule
  ],
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.scss']
})
export class MessageListComponent implements OnInit {

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  messages: Message[] = [];
  displayedColumns: string[] = ['id', 'timestamp', 'content', 'actions'];
  loading = true;
  error: string | null = null;
  formatDate = formatDate;

  // Pagination
  totalElements = 0;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [5, 10, 25, 50];

  constructor(
    private messageService: MessageService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadMessages();
  }

  loadMessages(): void {
    this.loading = true;
    this.error = null;

    this.messageService.getMessages(this.pageIndex, this.pageSize).subscribe({
      next: (response) => {
        this.messages = response.content;
        this.totalElements = response.totalElements;
        this.loading = false;
      },
      error: (error) => {
        this.error = error;
        this.loading = false;
      }
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadMessages();
  }

  openMessageDetail(message: Message): void {
    this.dialog.open(MessageDetailComponent, {
      width: '600px',
      data: message
    });
  }

  refreshMessages() {
    this.loadMessages();
  }
}
