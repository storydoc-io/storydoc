package io.storydoc.server.ui.infra.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UIScenario {

    private List<String> imageUrls;

}
