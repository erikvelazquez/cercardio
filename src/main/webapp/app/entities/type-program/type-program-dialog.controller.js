(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Type_ProgramDialogController', Type_ProgramDialogController);

    Type_ProgramDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Type_Program', 'Programs'];

    function Type_ProgramDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Type_Program, Programs) {
        var vm = this;

        vm.type_Program = entity;
        vm.clear = clear;
        vm.save = save;
        vm.programs = Programs.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.type_Program.id !== null) {
                Type_Program.update(vm.type_Program, onSaveSuccess, onSaveError);
            } else {
                Type_Program.save(vm.type_Program, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:type_ProgramUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
