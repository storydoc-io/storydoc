import {Component, ElementRef, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {FolderRestControllerService} from "../api/services/folder-rest-controller.service";
import {Observable} from "rxjs";
import {FolderDto} from "../api/models/folder-dto";
import {FormControl} from "@angular/forms";
import {FolderUrn} from "../api/models/folder-urn";

@Component({
  selector: 'app-folders-component',
  templateUrl: './folders-page.component.html',
  styleUrls: ['./folders-page.component.css']
})
export class FoldersPageComponent implements OnInit {

  constructor(private modalService: NgbModal, private folderRestControllerService: FolderRestControllerService) {}

  folders$ : Observable<FolderDto[]>

  parentFolder: FolderUrn

  ngOnInit(): void {
    this.folderRestControllerService.getRootFolderUsingGet({}).subscribe({
      next: rootFolderUrn => {
        this.parentFolder = rootFolderUrn
        this.refreshFolders()
      }
    })
  }

  private refreshFolders() {
    this.folders$ = this.folderRestControllerService.getFoldersUsingGet({ path: this.parentFolder.path})
  }

  openFolder(folder: FolderDto) {
    this.parentFolder = folder.urn
    this.refreshFolders()
  }

  // region "add folder dialog"
  @ViewChild('add_folder_dialog', { static: true }) addFolderDialog: ElementRef

  folderName: FormControl = new FormControl()

  addFolder() {
    this.dialog(this.addFolderDialog)
  }

  dialog(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.saveNewFolder()
    }, (reason) => {
    });
  }

  saveNewFolder() {
    this.folderRestControllerService.addFolderUsingPost({ path: this.parentFolder.path, folderName: this.folderName.value }).subscribe({
      next: value => this.refreshFolders()
    })
    this.folderName.reset()
  }
  // endregion
}
