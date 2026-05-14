package org.sitenetsoft.quarkus.pha.it;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.InputStream;
import java.util.Map;

/**
 * Minimal WOPI host endpoint serving the document-editor demo files.
 * Implements CheckFileInfo and GetFile so Collabora Online can render
 * the documents under integration-tests resources/documents/.
 *
 * Saving is accepted but discarded — the demo files are read-only.
 */
@Path("/wopi/files")
public class WopiResource {

    /** Map of WOPI file id → (resource path, display filename, read-only). */
    private static final Map<String, FileEntry> FILES = Map.of(
        "welcome", new FileEntry("documents/welcome.odt",  "Welcome.odt",          false),
        "budget",  new FileEntry("documents/budget.ods",   "Budget.ods",           false),
        "slides",  new FileEntry("documents/slides.odp",   "Quarterly Slides.odp", false),
        "policy",  new FileEntry("documents/policy.pdf",   "Policy.pdf",           true)
    );

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkFileInfo(@PathParam("id") String id) {
        FileEntry entry = FILES.get(id);
        if (entry == null) throw new NotFoundException();
        long size = entry.size();
        String json = "{"
            + "\"BaseFileName\":" + jsonString(entry.fileName) + ","
            + "\"Size\":" + size + ","
            + "\"OwnerId\":\"quarkus-pha\","
            + "\"UserId\":\"demo-user\","
            + "\"UserFriendlyName\":\"Demo User\","
            + "\"Version\":\"v1\","
            + "\"UserCanWrite\":" + (!entry.readOnly) + ","
            + "\"UserCanNotWriteRelative\":true,"
            + "\"SupportsUpdate\":" + (!entry.readOnly) + ","
            + "\"DisablePrint\":false,"
            + "\"DisableExport\":false,"
            + "\"DisableCopy\":false"
            + "}";
        return Response.ok(json).build();
    }

    @GET
    @Path("/{id}/contents")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile(@PathParam("id") String id) {
        FileEntry entry = FILES.get(id);
        if (entry == null) throw new NotFoundException();
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(entry.resource);
        if (in == null) throw new NotFoundException();
        return Response.ok(in).build();
    }

    @POST
    @Path("/{id}/contents")
    public Response putFile(@PathParam("id") String id) {
        FileEntry entry = FILES.get(id);
        if (entry == null) throw new NotFoundException();
        return Response.ok().build();
    }

    private static String jsonString(String s) {
        StringBuilder sb = new StringBuilder("\"");
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"'  -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> sb.append(c);
            }
        }
        return sb.append('"').toString();
    }

    private record FileEntry(String resource, String fileName, boolean readOnly) {
        long size() {
            try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource)) {
                if (in == null) return 0;
                return in.readAllBytes().length;
            } catch (Exception e) {
                return 0;
            }
        }
    }
}
