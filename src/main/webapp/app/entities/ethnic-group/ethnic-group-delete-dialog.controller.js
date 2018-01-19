(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('EthnicGroupDeleteController',EthnicGroupDeleteController);

    EthnicGroupDeleteController.$inject = ['$uibModalInstance', 'entity', 'EthnicGroup'];

    function EthnicGroupDeleteController($uibModalInstance, entity, EthnicGroup) {
        var vm = this;

        vm.ethnicGroup = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EthnicGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
