(function() {
    'use strict';

    angular 
        .module('epressingApp')
        .controller('CartController', CartController);

    CartController.$inject = ['$scope', '$stateParams', 'DataUtils', '$rootScope','entity', 'Produit','Caracteristique', 'LigneCommande', 'Operation', 'Tarif'];

    function CartController ($scope, $stateParams,  DataUtils, $rootScope, entity,Produit,Caracteristique, LigneCommande, Operation, Tarif)  {
        var vm = this; 
        vm.order = entity; 
        vm.commande = $rootScope.maCommande;
        vm.lignesCommandes = $rootScope.lignesCommandes;
        console.log($rootScope.lignesCommandes.length+' lignes de Commande dans $rootScope');
        console.log(vm.lignesCommandes.length+' lignes de Commande dans vm.lignesCommandes ');
        vm.operations = Operation.query();        
        vm.save = save;

              

        function save () {
            $rootScope.lignesCommandes.push(vm.ligneCommande);
            console.log('la taille de la liste de commande est maintenant: '+$rootScope.lignesCommandes.length);
        };        

        
 }
})();
