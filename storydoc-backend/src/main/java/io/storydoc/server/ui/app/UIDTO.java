package io.storydoc.server.ui.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UIDTO {

    private UIId uiId;

    private String imgURL;

}
