(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('ProduitDetailController', ProduitDetailController);

    ProduitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Produit', 'tarifs'];

    function ProduitDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Produit, tarifs) {
        var vm = this;

        vm.produit = entity;
        vm.tarifs = tarifs;
        //vm.tarifs = Produit.getTarifByProductID({idProduit:$stateParams.id}).$promise;
        //console.log('mes tarifs '+tarifs);
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile; 

        var unsubscribe = $rootScope.$on('epressingApp:produitUpdate', function(event, result) {
            vm.produit = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
