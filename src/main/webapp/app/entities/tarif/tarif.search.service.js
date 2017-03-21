(function() {
    'use strict';

    angular
        .module('epressingApp')
        .factory('TarifSearch', TarifSearch);

    TarifSearch.$inject = ['$resource'];

    function TarifSearch($resource) {
        var resourceUrl =  'api/_search/tarifs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
