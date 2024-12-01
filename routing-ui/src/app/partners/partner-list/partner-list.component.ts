import {Component, OnInit} from '@angular/core';
import {AddPartnerComponent} from "../add-partner/add-partner.component";
import {PartnerService} from "../services/partner.service";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {Partner} from "../models/partner";
import {CommonModule} from "@angular/common";
import {MatTableModule} from "@angular/material/table";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {LoadingSpinnerComponent} from "../../shared/components/loading-spinner/loading-spinner.component";
import {ErrorMessageComponent} from "../../shared/components/error-message/error-message.component";
import {ConfirmationDialogComponent} from "../confirmation-dialog/confirmation-dialog.component";
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";
import {MatPaginatorModule, PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-partner-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule,
    MatPaginatorModule,
    MatSnackBarModule,
    LoadingSpinnerComponent,
    ErrorMessageComponent,
    AddPartnerComponent,
    ConfirmationDialogComponent
  ],
  templateUrl: './partner-list.component.html',
  styleUrl: './partner-list.component.scss'
})
export class PartnerListComponent implements OnInit {

  partners: Partner[] = [];
  displayedColumns: string[] = ['alias', 'type', 'direction', 'application', 'processedFlowType', 'description', 'actions'];
  loading = false;
  error: string | null = null;

  totalElements = 0;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [5, 10, 25, 50];

  constructor(
    private partnerService: PartnerService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadPartners();
  }

  loadPartners(): void {
    this.loading = true;
    this.error = null;

    this.partnerService.getPartners(this.pageIndex, this.pageSize).subscribe({
      next: (partners) => {
        this.partners = partners.content;
        this.loading = false;
        this.totalElements = partners.totalElements;
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
    this.loadPartners();
  }

  openAddPartnerDialog(): void {
    const dialogRef = this.dialog.open(AddPartnerComponent, {
      width: '600px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadPartners();
      }
    });
  }

  deletePartner(partner: Partner): void {
    if (!partner || !partner.id) {
      console.error('Invalid partner object:', partner);
      return;
    }

    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '400px',
      data: {
        title: 'Delete Partner',
        message: `Are you sure you want to delete the partner "${partner.alias}"? This action cannot be undone.`,
        confirmText: 'Delete',
        cancelText: 'Cancel'
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loading = true;
        this.partnerService.deletePartner(partner.id).subscribe({
          next: () => {
            this.loadPartners();
            this.snackBar.open('Partner deleted successfully', 'Close', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top'
            });
          },
          error: (error) => {
            this.error = error;
            this.loading = false;
            this.snackBar.open('Failed to delete partner', 'Close', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top',
              panelClass: ['error-snackbar']
            });
          }
        });
      }
    });
  }

  refreshPartners() {
    this.loadPartners();
  }
}
