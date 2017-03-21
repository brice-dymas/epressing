(function() {
    'use strict';
    angular
        .module('epressingApp')
        .factory('Tarif', Tarif);

    Tarif.$inject = ['$resource'];

    function Tarif ($resource) {
        var resourceUrl =  'api/tarifs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
