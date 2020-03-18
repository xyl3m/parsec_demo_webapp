package com.xyl3m.demo.parsec;

import static com.google.common.truth.Truth.assertThat;

import com.xyl3m.demo.parsec.enumtype.ResourceExceptionType;
import com.xyl3m.demo.parsec.parsec_generated.Pagination;
import com.xyl3m.demo.parsec.parsec_generated.ParsecErrorDetail;
import com.xyl3m.demo.parsec.parsec_generated.ParsecResourceError;
import com.xyl3m.demo.parsec.parsec_generated.ResourceException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public final class ValidationUtil {

  private static final int DIVIDE_NUMBER = 100000;

  /**
   * Constructor.
   */
  private ValidationUtil() {
  }

  /**
   * Validate ResourceException with the type.
   *
   * @param e    ResourceException
   * @param type ResourceExceptionType
   */
  public static void validateResourceException(ResourceException e, ResourceExceptionType type) {
    assertThat(e.getCode()).isEqualTo(type.code() / DIVIDE_NUMBER);

    ParsecResourceError pre = (ParsecResourceError) e.getData();

    assertThat(pre.getError().getCode()).isEqualTo(type.code());
    assertThat(pre.getError().getMessage()).isEqualTo(type.message());
  }

  /**
   * Validate ResourceException with ParsecErrorDetail.
   *
   * @param e               ResourceException
   * @param type            ResourceExceptionType
   * @param expectedDetails list of ParsecErrorDetail
   */
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  public static void validateResourceException(ResourceException e, ResourceExceptionType type,
      List<ParsecErrorDetail> expectedDetails) {
    assertThat(e.getCode()).isEqualTo(type.statusCode());
    ParsecResourceError error = (ParsecResourceError) e.getData();
    assertThat(error.getError().getCode()).isEqualTo(type.code());
    assertThat(error.getError().getMessage()).isEqualTo(type.message());
    List<ParsecErrorDetail> details = error.getError().getDetail();
    details.sort(Comparator.comparing(ParsecErrorDetail::getMessage));
    details.sort(Comparator.comparing(parsecErrorDetail ->
        StringUtils.isNotBlank(parsecErrorDetail.getInvalidValue()) ? parsecErrorDetail
            .getInvalidValue() : StringUtils.EMPTY
    ));
    expectedDetails.sort(Comparator.comparing(ParsecErrorDetail::getMessage));
    expectedDetails.sort(Comparator.comparing(parsecErrorDetail ->
        StringUtils.isNotBlank(parsecErrorDetail.getInvalidValue()) ? parsecErrorDetail
            .getInvalidValue() : StringUtils.EMPTY
    ));
    if (CollectionUtils.isNotEmpty(expectedDetails)) {
      IntStream.range(0, expectedDetails.size()).forEach(i -> {
        assertThat(details.get(i).getMessage()).isEqualTo(expectedDetails.get(i).getMessage());
        assertThat(details.get(i).getInvalidValue())
            .isEqualTo(expectedDetails.get(i).getInvalidValue());
      });
    }
  }

  /**
   * Validate Pagination.
   *
   * @param p            Pagination instance
   * @param resultsTotal total amount of results
   * @param nextOffset   next offset
   */
  public static void validatePagination(Pagination p, int resultsTotal, Integer nextOffset) {
    assertThat(p).isNotNull();
    assertThat(p.getResultsTotal()).isEqualTo(resultsTotal);
    assertThat(p.getNextOffset()).isEqualTo(nextOffset);
  }

}
