(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('TarifDetailController', TarifDetailController);

    TarifDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tarif', 'Operation', 'Produit'];

    function TarifDetailController($scope, $rootScope, $stateParams, previousState, entity, Tarif, Operation, Produit) {
        var vm = this;

        vm.tarif = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('epressingApp:tarifUpdate', function(event, result) {
            vm.tarif = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
